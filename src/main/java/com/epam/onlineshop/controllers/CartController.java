package com.epam.onlineshop.controllers;

import com.epam.onlineshop.entities.User;
import com.epam.onlineshop.services.ProductInOrderService;
import com.epam.onlineshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final ProductInOrderService productInOrderService;
    private final UserService userService;

    @GetMapping(value = "/cart")
    public ModelAndView openCart(ModelAndView model, Principal principal) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);
        model.addObject("products", productInOrderService.findAllNewOrderByUser(currentUser));
        model.setViewName("cart");
        return model;
    }

    @GetMapping(value = "/cart/payment")
    public ModelAndView openPayment(ModelAndView model, Principal principal) {
        model.addObject("isPaid", false);
        model.setViewName("payment0");
        return model;
    }

    @GetMapping(value = "/cart/order")
    public ModelAndView payOrderGet(ModelAndView model, Principal principal) {
        productInOrderService.makeOrder(userService.findByUsername(principal.getName()));
        model.addObject("isPaid", true);
        model.setViewName("payment0");
        return model;
    }

    @PostMapping(value = "/cart/order")
    public ModelAndView payOrder(ModelAndView model, Principal principal) {
        productInOrderService.makeOrder(userService.findByUsername(principal.getName()));
        model.addObject("isPaid", true);
        model.setViewName("payment0");
        return model;
    }

    @RequestMapping(value = "/cart/{id}/delete", method = RequestMethod.GET)
    public ModelAndView deleteProduct(@PathVariable("id") Long id, ModelAndView model) {
        productInOrderService.deleteById(id);
        model.setViewName("redirect:/cart");
        return model;
    }

    @RequestMapping(value = "/cart/{id}/inc", method = RequestMethod.GET)
    public ModelAndView incrementCount(@PathVariable("id") Long id, ModelAndView model) {
        productInOrderService.incrementCount(id);
        model.setViewName("redirect:/cart");
        return model;
    }

    @RequestMapping(value = "/cart/{id}/decrement", method = RequestMethod.GET)
    public ModelAndView decrementCount(@PathVariable("id") Long id, ModelAndView model) {
        if (productInOrderService.getQuantityById(id) > 1) {
            productInOrderService.decrementCount(id);
        } else {
            productInOrderService.deleteById(id);
        }
        model.setViewName("redirect:/cart");
        return model;
    }

    @GetMapping(value = "/cart/{id}/add")
    public ModelAndView addProductInCart(@PathVariable("id") Long productId, ModelAndView model, Principal principal) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);
        productInOrderService.addOrderInCart(productId, currentUser);
        model.setViewName("redirect:/welcome");
        return model;
}
}
