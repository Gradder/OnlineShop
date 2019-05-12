package com.epam.onlineshop.services;

import com.epam.onlineshop.entities.ProductInOrder;
import com.epam.onlineshop.entities.User;

import java.util.List;

public interface ProductInOrderService {

    List<ProductInOrder> findAllOrderedByUser(User user);

    ProductInOrder addOrderInCart(Long productId, User user);

    List<ProductInOrder> findAllNewOrderByUser(User user);

    void deleteById(Long id);

    void incrementCount(Long id);

    void decrementCount(Long id);

    Integer getQuantityById(Long id);

    void makeOrder(User user);

    List<ProductInOrder> getProductsFromThisOrder(Long id);

    List<ProductInOrder> saveProductsInOrder(List<ProductInOrder> productsInOrder);
}
