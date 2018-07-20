package com.epam.onlineshop.services;

import com.epam.onlineshop.entities.Category;
import com.epam.onlineshop.entities.Product;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    List<Product> getAllProductsWithPageable(Pageable page);
    int getCountByCategory(Category category);
    int getCountAll();
    List<Product> findAllProductsByCategory(Pageable page, Category category);

    boolean addNewProduct(Product product);
    boolean isProductExist(String name);
    Product getProductById(Long id);
    boolean saveProduct(Product product);
    boolean deleteProductById(Long id);
}
