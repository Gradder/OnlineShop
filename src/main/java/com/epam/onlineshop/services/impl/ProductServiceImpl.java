package com.epam.onlineshop.services.impl;

import com.epam.onlineshop.entities.Category;
import com.epam.onlineshop.entities.Product;
import com.epam.onlineshop.repository.ProductRepository;
import com.epam.onlineshop.services.ProductService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsWithPageable(Pageable page) {
        return productRepository.findAllWithPageable(page).getContent();
    }

    @Override
    public int getCountByCategory(Category category) {
        return productRepository.findAllByCategory(category);
    }

    @Override
    public int getCountAll() {
        return productRepository.getCountAll();
    }

    @Override
    public List<Product> findAllProductsByCategory(Pageable page, Category category) {
        Page<Product> allByCategory = productRepository.findAllByCategoryAndPageable(category, page);
        return allByCategory.getContent();
    }

    @Override
    public boolean addNewProduct(Product product) {
        if (!isProductExist(product.getName())) {
            productRepository.save(product);
        }

        logger.info("Product " + product.getName() + " was added in DB.");
        return isProductExist(product.getName());
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> byId = productRepository.findById(id);
        return byId.orElse(null);
    }

    public boolean isProductExist(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public boolean saveProduct(Product product) {
        productRepository.save(product);
        return isProductExist(product.getName());
    }

    @Override
    public boolean deleteProductById(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
        return (!isProductExist(product.getName()));
    }

}
