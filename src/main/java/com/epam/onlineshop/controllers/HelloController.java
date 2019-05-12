package com.epam.onlineshop.controllers;

import com.epam.onlineshop.entities.Role;
import com.epam.onlineshop.entities.User;
import com.epam.onlineshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class HelloController {

  private final UserService userService;

  @SuppressWarnings("deprecation")
  @GetMapping({"/", "/welcome"})
  public ModelAndView main() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User loggedInUser = userService.findByUsername(username);

    String viewName = getViewName(loggedInUser.getRole());
    return new ModelAndView(viewName);
  }

  @GetMapping("/deny-access")
  public ModelAndView denyAccess() {
    return new ModelAndView("access_denied");
  }

  private String getViewName(Role role) {
    if (role.equals(Role.ADMIN)) {
      return "redirect:/users";
    } else {
      return "main";
    }
  }
}

