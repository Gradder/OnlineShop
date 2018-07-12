package com.epam.onlineshop.services;

import com.epam.onlineshop.entities.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    void deleteById(Long id);

    void incrementCount(Long id);

    void decrementCount(Long id);

    Integer getCountById(Long id);
}