package com.epam.onlineshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.epam.onlineshop.entities.Order;
import com.epam.onlineshop.entities.Status;
import com.epam.onlineshop.repository.OrderRepository;
import com.epam.onlineshop.services.impl.OrderServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

  @Test
  public void saveOrder() {
    when(orderRepository.save(order)).thenReturn(order);

    Order actual = service.saveOrder(order);

    assertEquals("Returned object doesn't match", order, actual);
    verify(orderRepository).save(order);
    verifyNoMoreInteractions(orderRepository);
  }

  @Test
  public void setStatusById() {
    Long orderId = 1L;
    Status orderStatus = Status.NEW;
    Integer expected = 1;
    when(order.getId()).thenReturn(orderId);
    when(order.getStatus()).thenReturn(orderStatus);
    when(orderRepository.setStatusById(orderStatus, orderId)).thenReturn(expected);

    Integer actual = service.setStatusById(order, orderId);

    assertEquals("Returned object doesn't match", expected, actual);
    verify(orderRepository).setStatusById(orderStatus, orderId);
    verifyNoMoreInteractions(orderRepository);
  }

  @Test
  public void findById() {
    Long orderId = 1L;
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    Order actual = service.findById(orderId);

    assertEquals("Returned object doesn't match", order, actual);
    verify(orderRepository).findById(orderId);
    verifyNoMoreInteractions(orderRepository);
  }

  @Test
  public void findById_negative() {
    Long orderId = 1L;
    when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

    Order actual = service.findById(orderId);

    assertNull("Returned object doesn't match", actual);
    verify(orderRepository).findById(orderId);
    verifyNoMoreInteractions(orderRepository);
  }
}
