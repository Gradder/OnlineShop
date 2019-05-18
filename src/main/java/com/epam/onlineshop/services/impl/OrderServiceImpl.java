package com.epam.onlineshop.services.impl;

import com.epam.onlineshop.entities.Order;
import com.epam.onlineshop.repository.OrderRepository;
import com.epam.onlineshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> result = orderRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Integer setStatusById(Order order, Long id) {
        return orderRepository.setStatusById(order.getStatus(), id);
    }
}
