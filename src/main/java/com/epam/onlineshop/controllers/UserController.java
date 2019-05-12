package com.epam.onlineshop.controllers;

import com.epam.onlineshop.entities.ProductInOrder;
import com.epam.onlineshop.services.ProductInOrderService;
import com.epam.onlineshop.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import com.epam.onlineshop.entities.Role;
import com.epam.onlineshop.entities.User;
import com.epam.onlineshop.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {

    private static final String USER_JSP = "userJSP";
    private final UserService userService;
    private final UserValidator userValidator;
    private final ProductInOrderService productInOrderService;
    private static final Logger logger = Logger.getLogger(UserController.class);

    @GetMapping(value = "/registration")
    public ModelAndView openRegistrationForm(ModelAndView model) {
        model.addObject(USER_JSP, new User());
        model.setViewName("registration");
        return model;
    }

    @GetMapping(value = "/logout")
    public ModelAndView openRegistrationForm(ModelAndView model, HttpServletRequest request,
                                             HttpServletResponse response, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        model.setViewName("redirect:/login?logout");
        logger.info("User " + principal.getName() + "log out");
        return model;
    }

    @GetMapping(value = "/profile")
    public ModelAndView openProfile(ModelAndView model, Principal principal) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);
        model.addObject(USER_JSP, currentUser);
        List<ProductInOrder> allOrdersByUser = productInOrderService.findAllOrderedByUser(currentUser);
        model.addObject("products", allOrdersByUser);
        model.setViewName("profile");
        return model;
    }

    @GetMapping(value = "/edit")
    public ModelAndView openEditProfileForm(ModelAndView model, Principal principal) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);
        model.addObject(USER_JSP, currentUser);
        model.setViewName("edit_profile");
        return model;
    }

    @PostMapping(value = "/edit")
    public ModelAndView editProfile(@ModelAttribute("userJSP") User user, ModelAndView model, Principal principal) {
        user.setUsername(principal.getName());
        userService.updateUser(user);
        model.setViewName("redirect:/profile");
        return model;
    }

    @PostMapping(value = "/registration")
    public ModelAndView addUser(@ModelAttribute(USER_JSP) User user, BindingResult bindingResult, ModelAndView model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.setViewName("registration");
        } else {
            userService.addUser(user);
            model.setViewName("redirect:/login");
        }
        return model;
    }

    @GetMapping(value = "/login")
    public ModelAndView login(ModelAndView model, String error, String logout) {
        if (error != null) {
            model.addObject("error", "Your username and password is invalid.");
        }

        if (logout != null) {
            model.addObject("message", "You have been logged out successfully.");
        }
        model.setViewName("login");
        return model;
    }

    @GetMapping("/users")
    public ModelAndView getAllUsers(@ModelAttribute("user") User user, ModelAndView model) {
        model.setViewName("main_admin_users");
        model.addObject("user", new User());
        model.addObject(userService.getAllUsers());
        return model;
    }

    @PostMapping("/users/{id}/block")
    public ModelAndView changeBlockedStatus(@PathVariable Long id) {
        User user = userService.findById(id);

        if ((user != null) && (user.getRole() != Role.ADMIN)) {
            userService.changeBlockedStatus(user);
        }

        return new ModelAndView("redirect:/users");
    }
}