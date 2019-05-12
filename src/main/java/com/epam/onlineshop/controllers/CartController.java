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

    private static final String REDIRECT_CART = "redirect:/cart";
    private static final String PAYMENT_0 = "payment0";
    private final ProductInOrderService productInOrderService;
    private final UserService userService;
    private static final String IS_PAID = "isPaid";

    @GetMapping(value = "/cart")
    public ModelAndView openCart(ModelAndView model, Principal principal) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);
        model.addObject("products", productInOrderService.findAllNewOrderByUser(currentUser));
        model.setViewName("cart");
        return model;
    }

    @GetMapping(value = "/cart/payment")
    public ModelAndView openPayment(ModelAndView model) {
        model.addObject(IS_PAID, false);
        model.setViewName(PAYMENT_0);
        return model;
    }

    @GetMapping(value = "/cart/order")
    public ModelAndView payOrderGet(ModelAndView model, Principal principal) {
        productInOrderService.makeOrder(userService.findByUsername(principal.getName()));
        model.addObject(IS_PAID, true);
        model.setViewName(PAYMENT_0);
        return model;
    }

    @GetMapping(value = "/cart/{id}/delete")
    public ModelAndView deleteProduct(@PathVariable("id") Long id, ModelAndView model) {
        productInOrderService.deleteById(id);
        model.setViewName(REDIRECT_CART);
        return model;
    }

    @GetMapping(value = "/cart/{id}/inc")
    public ModelAndView incrementCount(@PathVariable("id") Long id, ModelAndView model) {
        productInOrderService.incrementCount(id);
        model.setViewName(REDIRECT_CART);
        return model;
    }

    @GetMapping(value = "/cart/{id}/decrement")
    public ModelAndView decrementCount(@PathVariable("id") Long id, ModelAndView model) {
        if (productInOrderService.getQuantityById(id) > 1) {
            productInOrderService.decrementCount(id);
        } else {
            productInOrderService.deleteById(id);
        }
        model.setViewName(REDIRECT_CART);
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
