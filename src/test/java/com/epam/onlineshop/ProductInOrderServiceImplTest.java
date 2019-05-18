package com.epam.onlineshop;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.epam.onlineshop.entities.Order;
import com.epam.onlineshop.entities.Product;
import com.epam.onlineshop.entities.ProductInOrder;
import com.epam.onlineshop.entities.Status;
import com.epam.onlineshop.entities.User;
import com.epam.onlineshop.repository.OrderRepository;
import com.epam.onlineshop.repository.ProductInOrderRepository;
import com.epam.onlineshop.repository.ProductRepository;
import com.epam.onlineshop.services.impl.OrderServiceImpl;
import com.epam.onlineshop.services.impl.ProductInOrderServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ProductInOrderServiceImplTest {

  @Mock
  private ProductInOrderRepository productInOrderRepository;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private OrderRepository orderRepository;

  @Mock
  private User user;
  @Mock
  private ProductInOrder productInOrder;
  @Mock
  private Product product;
  @Mock
  private Order order;

  private ProductInOrderServiceImpl service;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    service = Mockito.spy(new ProductInOrderServiceImpl(productRepository, productInOrderRepository,
        orderRepository));
  }

  @Test
  public void findAllNewOrderByUser() {
    List<ProductInOrder> expected = Collections.singletonList(productInOrder);
    when(productInOrderRepository.findAllNewOrderByUser(user))
        .thenReturn(expected);

    List<ProductInOrder> actual = service.findAllNewOrderByUser(user);

    assertEquals("Returned object doesn't match", expected, actual);
  }

  @Test
  public void findAllOrderedByUser() {
    List<ProductInOrder> expected = Collections.singletonList(productInOrder);
    when(productInOrderRepository.findAllOrderedByUser(user))
        .thenReturn(expected);

    List<ProductInOrder> actual = service.findAllOrderedByUser(user);

    assertEquals("Returned object doesn't match", expected, actual);
  }

  @Test
  public void addOrderInCart_exists() {
    Long productId = 1L;
    ProductInOrder expected = ProductInOrder.builder().quantity(1).build();
    when(productInOrderRepository.findOneOrderInCartByUserAndProductId(productId, user))
        .thenReturn(Optional.of(expected));
    when(productInOrderRepository.save(expected)).thenReturn(expected);

    ProductInOrder actual = service.addOrderInCart(productId, user);

    assertEquals("Returned object doesn't match", expected, actual);
    assertEquals("Returned object doesn't match", Integer.valueOf(2), actual.getQuantity());
  }

  @Test
  public void addOrderInCart_does_not_exists_order() {
    Long productId = 1L;
    ProductInOrder expected = ProductInOrder.builder().quantity(1).build();

    when(productRepository.getOne(productId)).thenReturn(product);
    when(orderRepository.getOneNewOrderByUser(user)).thenReturn(null);
    doReturn(order).when(orderRepository).save(any());
    when(productInOrderRepository.findOneOrderInCartByUserAndProductId(productId, user))
        .thenReturn(Optional.empty());
    when(productInOrderRepository.save(any())).thenReturn(expected);

    ProductInOrder actual = service.addOrderInCart(productId, user);

    assertEquals("Returned object doesn't match", expected, actual);
    assertEquals("Returned object doesn't match", Integer.valueOf(1), actual.getQuantity());
  }

  @Test
  public void addOrderInCart_exists_order() {
    Long productId = 1L;
    ProductInOrder expected = ProductInOrder.builder().quantity(1).build();

    when(productRepository.getOne(productId)).thenReturn(product);
    when(orderRepository.getOneNewOrderByUser(user)).thenReturn(order);
    when(productInOrderRepository.findOneOrderInCartByUserAndProductId(productId, user))
        .thenReturn(Optional.empty());
    when(productInOrderRepository.save(any())).thenReturn(expected);

    ProductInOrder actual = service.addOrderInCart(productId, user);

    assertEquals("Returned object doesn't match", expected, actual);
    assertEquals("Returned object doesn't match", Integer.valueOf(1), actual.getQuantity());

    verify(orderRepository).getOneNewOrderByUser(user);
    verifyNoMoreInteractions(orderRepository);
  }

  @Test
  public void deleteById() {
    Long productInOrderId = 1L;

    service.deleteById(productInOrderId);

    verify(productInOrderRepository).deleteById(productInOrderId);
    verifyNoMoreInteractions(productInOrderRepository);
  }

  @Test
  public void incrementCount() {
    Long productInOrderId = 1L;
    when(productInOrder.getProduct()).thenReturn(product);
    when(product.getName()).thenReturn("");
    when(productInOrder.getOrder()).thenReturn(order);
    when(order.getUser()).thenReturn(user);
    when(user.getUsername()).thenReturn("");
    when(productInOrder.getQuantity()).thenReturn(1);
    when(productInOrderRepository.findById(productInOrderId))
        .thenReturn(Optional.of(productInOrder));

    service.incrementCount(productInOrderId);

    verify(productInOrder).setQuantity(2);
    verify(productInOrderRepository).findById(productInOrderId);
    verify(productInOrderRepository).save(productInOrder);
    verifyNoMoreInteractions(productInOrderRepository);
  }

  @Test
  public void incrementCount_notFound() {
    Long productInOrderId = 1L;
    when(productInOrderRepository.findById(productInOrderId))
        .thenReturn(Optional.empty());

    service.incrementCount(productInOrderId);

    verify(productInOrderRepository).findById(productInOrderId);
    verifyNoMoreInteractions(productInOrderRepository);
  }

  @Test
  public void decrementCount() {
    Long productInOrderId = 1L;
    when(productInOrder.getProduct()).thenReturn(product);
    when(product.getName()).thenReturn("");
    when(productInOrder.getOrder()).thenReturn(order);
    when(order.getUser()).thenReturn(user);
    when(user.getUsername()).thenReturn("");
    when(productInOrder.getQuantity()).thenReturn(1);
    when(productInOrderRepository.findById(productInOrderId))
        .thenReturn(Optional.of(productInOrder));

    service.decrementCount(productInOrderId);

    verify(productInOrder).setQuantity(0);
    verify(productInOrderRepository).findById(productInOrderId);
    verify(productInOrderRepository).save(productInOrder);
    verifyNoMoreInteractions(productInOrderRepository);
  }

  @Test
  public void decrementCount_notFound() {
    Long productInOrderId = 1L;
    when(productInOrderRepository.findById(productInOrderId))
        .thenReturn(Optional.empty());

    service.decrementCount(productInOrderId);

    verify(productInOrderRepository).findById(productInOrderId);
    verifyNoMoreInteractions(productInOrderRepository);
  }

  @Test
  public void getQuantityById() {

    Long productInOrderId = 1L;
    when(productInOrder.getQuantity()).thenReturn(1);
    when(productInOrderRepository.findById(productInOrderId))
        .thenReturn(Optional.of(productInOrder));

    Integer actual = service.getQuantityById(productInOrderId);

    assertEquals("Returned object doesn't match", Integer.valueOf(1), actual);
  }

  @Test
  public void getQuantityById_notFound() {

    Long productInOrderId = 1L;
    when(productInOrder.getQuantity()).thenReturn(1);
    when(productInOrderRepository.findById(productInOrderId))
        .thenReturn(Optional.empty());

    Integer actual = service.getQuantityById(productInOrderId);

    assertEquals("Returned object doesn't match", Integer.valueOf(0), actual);
  }

  @Test
  public void makeOrder() {

    List<ProductInOrder> productInOrders = Collections.singletonList(productInOrder);
    when(productInOrderRepository.findAllNewOrderByUser(user))
        .thenReturn(productInOrders);

    when(productInOrder.getOrder()).thenReturn(order);

    service.makeOrder(user);

    verify(order).setStatus(Status.PREPAID);
    verify(productInOrderRepository).findAllNewOrderByUser(user);
    verify(productInOrderRepository).saveAll(productInOrders);
    verifyNoMoreInteractions(productInOrderRepository);
  }

  @Test
  public void makeOrder_notFound() {

    when(productInOrderRepository.findAllNewOrderByUser(user))
        .thenReturn(Collections.emptyList());

    service.makeOrder(user);

    verify(productInOrderRepository).findAllNewOrderByUser(user);
    verify(productInOrderRepository).saveAll(Collections.emptyList());
    verifyNoMoreInteractions(productInOrderRepository);
  }

  @Test
  public void getProductsFromThisOrder() {

    Long orderId = 1L;

    List<ProductInOrder> expected = Collections.singletonList(productInOrder);
    when(productInOrderRepository.findByOrderId(orderId))
        .thenReturn(expected);

    List<ProductInOrder> actual = service.getProductsFromThisOrder(orderId);

    assertEquals("Returned object doesn't match", expected, actual);

    verify(productInOrderRepository).findByOrderId(orderId);
    verifyNoMoreInteractions(productInOrderRepository);
  }

  @Test
  public void saveProductsInOrder() {

    List<ProductInOrder> expected = Collections.singletonList(productInOrder);
    when(productInOrderRepository.saveAll(expected)).thenReturn(expected);

    List<ProductInOrder> actual = service.saveProductsInOrder(expected);

    assertEquals("Returned object doesn't match", expected, actual);

    verify(productInOrderRepository).saveAll(expected);
    verifyNoMoreInteractions(productInOrderRepository);
  }
}
