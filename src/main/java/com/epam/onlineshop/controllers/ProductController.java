package com.epam.onlineshop.controllers;

import com.epam.onlineshop.entities.Product;
import com.epam.onlineshop.entities.Role;
import com.epam.onlineshop.entities.User;
import com.epam.onlineshop.services.ProductService;
import com.epam.onlineshop.services.UserService;
import com.epam.onlineshop.utils.ImageWriter;
import com.epam.onlineshop.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  private final ProductValidator productValidator;

  private final UserService userService;

  @GetMapping("/catalog")
  public ModelAndView getAllProducts(ModelAndView model) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findByUsername(username);
    model.setViewName(getViewName(user.getRole()));
    model.addObject(productService.getAllProducts());
    return model;
  }

  @PostMapping("/products")
  public ModelAndView addProduct(@ModelAttribute("product") Product product,
      BindingResult bindingResult, @RequestParam("file") MultipartFile file) {
    ModelAndView catalog = new ModelAndView();
    productValidator.validate(product, bindingResult);

    catalog.setViewName(getViewName(Role.ADMIN));
    if (!bindingResult.hasErrors()) {
      long currentTime = new Date().getTime();
      String name = String.valueOf(currentTime);

      if (file.isEmpty()) {
        product.setImageLink("default.jpg");
      } else {
        ImageWriter.writeImage(catalog, file, name);
        product.setImageLink(name + ".jpg");
      }
      productService.addNewProduct(product);
    }

    catalog.addObject(productService.getAllProducts());
    return catalog;
  }

  @GetMapping("/products/add")
  public ModelAndView getProductForm(ModelAndView model) {
    model.addObject("product", new Product());
    model.setViewName("add_product");
    return model;
  }

  @GetMapping("/products/{id}/edit")
  public ModelAndView editProduct(@PathVariable Long id, ModelAndView model) {
    model.setViewName("edit_product");
    model.addObject("product", productService.getProductById(id));
    return model;
  }

  @PostMapping("/products/{id}/save")
  public ModelAndView saveProduct(@PathVariable Long id,
      @ModelAttribute("product") Product product, BindingResult bindingResult,
      @RequestParam("file") MultipartFile file) {
    ModelAndView model = new ModelAndView();
    productValidator.validate(product, bindingResult);

    model.setViewName(getViewName(Role.ADMIN));
    long currentTime = new Date().getTime();
    String name = String.valueOf(currentTime);

    if (file.isEmpty()) {
      product.setImageLink(productService.getProductById(id).getImageLink());
    } else {
      ImageWriter.writeImage(model, file, name);
      product.setImageLink(name + ".jpg");
    }

    productService.saveProduct(product);

    model.addObject(productService.getAllProducts());
    return model;
  }

  @PostMapping("/products/{id}/delete")
  public ModelAndView deleteProduct(@PathVariable Long id) {
    productService.deleteProductById(id);
    return new ModelAndView("redirect:/catalog");
  }

  private String getViewName(Role role) {
    if (role.equals(Role.ADMIN)) {
      return "main_admin_products";
    } else {
      return "main";
    }
  }
}
