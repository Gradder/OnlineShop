package com.epam.onlineshop.controllers;

import com.epam.onlineshop.entities.Category;
import com.epam.onlineshop.entities.Role;
import com.epam.onlineshop.entities.User;
import com.epam.onlineshop.repository.ProductRepository;
import com.epam.onlineshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import com.epam.onlineshop.entities.Product;
import com.epam.onlineshop.services.ProductService;
import org.apache.log4j.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final ProductService productService;
    private final static Logger logger = Logger.getLogger(HelloController.class);

    private final UserService userService;

    @GetMapping({"/","/welcome"})
    public ModelAndView main(@RequestParam(value = "page", required = false) Integer numOfPage, @RequestParam(value = "category", required = false) Category category) {
        int numOfProductsOnPage = 3;
        boolean isAll = false;
        if((numOfPage == null)||(numOfPage<0)) {
            numOfPage = 1;
        }
        numOfPage--;
        if((category == null)) {
            isAll = true;
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.findByUsername(username);

        String viewName = getViewName(loggedInUser.getRole());
        ModelAndView modelAndView = new ModelAndView(viewName);
        if (loggedInUser.getRole().equals(Role.USER)){

            List<Product> product;
            double temp;

            Pageable page = new PageRequest(numOfPage, numOfProductsOnPage);

            if(isAll){
                product = productService.getAllProductsWithPageable(page);
                temp = productService.getCountAll()/(double)numOfProductsOnPage;
            }else{
                product = productService.findAllProductsByCategory(page, category);
                temp = productService.getCountByCategory(category)/(double)numOfProductsOnPage;
            }

        modelAndView.addObject(product);
        int numOfPages = (int)Math.ceil(temp);
        modelAndView.addObject("pages", numOfPages);
        List<Category> categories = Arrays.asList(Category.values());
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("product", new Product());
        }
        return modelAndView;
    }

    @GetMapping("/deny-access")
    public ModelAndView denyAccess() {
        return new ModelAndView("access_denied");
    }

    private String getViewName(Role role) {
        switch (role) {
            case ADMIN:
                return "redirect:/users";
            default:
                return "main";
        }
    }
}

