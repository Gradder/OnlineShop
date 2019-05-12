package com.epam.onlineshop.services;

import com.epam.onlineshop.entities.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();
    Order findById(Long id);
    Order saveOrder(Order order);
    Integer setStatusById(Order order, Long id);
}
