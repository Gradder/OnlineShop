package com.epam.onlineshop;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.epam.onlineshop.entities.Order;
import com.epam.onlineshop.repository.OrderRepository;
import com.epam.onlineshop.services.impl.OrderServiceImpl;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class OrderServiceImplTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private Order order;

  private OrderServiceImpl service;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    service = Mockito.spy(new OrderServiceImpl(orderRepository));
  }

  @Test
  public void getAllOrders() {
    List<Order> expected = Collections.singletonList(order);
    when(orderRepository.findAll()).thenReturn(expected);

    List<Order> actual = service.getAllOrders();

    assertEquals("Returned object doesn't match", expected, actual);
  }
}
