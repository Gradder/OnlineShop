package com.epam.onlineshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.epam.onlineshop.entities.Category;
import com.epam.onlineshop.entities.Product;
import com.epam.onlineshop.repository.ProductRepository;
import com.epam.onlineshop.services.impl.ProductServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;
  @Mock
  private Pageable page;
  @Mock
  private Product product;
  @Mock
  private Page<Product> productPage;

  private ProductServiceImpl service;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    service = Mockito.spy(new ProductServiceImpl(productRepository));
  }

  @Test
  public void getAllProducts() {
    List<Product> products = Collections.singletonList(product);
    when(productRepository.findAll()).thenReturn(products);

    List<Product> actual = service.getAllProducts();

    assertEquals("Returned object doesn't match", products, actual);
    verify(productRepository).findAll();
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  public void getCountAll() {
    when(productRepository.getCountAll()).thenReturn(1);

    int actual = service.getCountAll();

    assertEquals("Returned object doesn't match", 1, actual);
    verify(productRepository).getCountAll();
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  public void getCountByCategory() {
    when(productRepository.findAllByCategory(Category.STAR_WARS)).thenReturn(1);

    int actual = service.getCountByCategory(Category.STAR_WARS);

    assertEquals("Returned object doesn't match", 1, actual);
    verify(productRepository).findAllByCategory(Category.STAR_WARS);
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  public void findAllProductsByCategory() {
    List<Product> products = Collections.singletonList(product);

    when(productPage.getContent()).thenReturn(products);
    when(productRepository.findAllByCategoryAndPageable(Category.STAR_WARS, page))
        .thenReturn(productPage);

    List<Product> actual = service.findAllProductsByCategory(page, Category.STAR_WARS);

    assertEquals("Returned object doesn't match", products, actual);
    verify(productRepository).findAllByCategoryAndPageable(Category.STAR_WARS, page);
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  public void getAllProductsWithPageable() {
    List<Product> products = Collections.singletonList(product);
    when(productPage.getContent()).thenReturn(products);
    when(productRepository.findAllWithPageable(page)).thenReturn(productPage);

    List<Product> actual = service.getAllProductsWithPageable(page);

    assertEquals("Returned object doesn't match", products, actual);

    verify(productRepository).findAllWithPageable(page);
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  public void addNewProduct() {

    String productName = "";
    when(product.getName()).thenReturn(productName);
    when(service.isProductExist(productName)).thenReturn(Boolean.TRUE);

    boolean actual = service.addNewProduct(product);

    assertEquals("Returned object doesn't match", Boolean.FALSE, actual);

  }

  @Test
  public void addNewProduct_new() {

    String productName = "";
    when(product.getName()).thenReturn(productName);
    when(productRepository.save(product)).thenReturn(product);
    when(service.isProductExist(productName)).thenReturn(Boolean.FALSE);

    boolean actual = service.addNewProduct(product);

    assertEquals("Returned object doesn't match", Boolean.TRUE, actual);

  }

  @Test
  public void addNewProduct_new_negative() {

    String productName = "";
    when(product.getName()).thenReturn(productName);
    when(productRepository.save(product)).thenReturn(null);
    when(service.isProductExist(productName)).thenReturn(Boolean.FALSE);

    boolean actual = service.addNewProduct(product);

    assertEquals("Returned object doesn't match", Boolean.FALSE, actual);

  }

  @Test
  public void getProductById() {

    Long productId = 1L;
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    Product actual = service.getProductById(productId);

    assertEquals("Returned object doesn't match", product, actual);
  }

  @Test
  public void getProductById_negative() {

    Long productId = 1L;
    when(productRepository.findById(productId)).thenReturn(Optional.empty());
    Product actual = service.getProductById(productId);

    assertNull("Returned object doesn't match", actual);
  }

  @Test
  public void isProductExist() {

    String productName = "";
    when(productRepository.existsByName(productName)).thenReturn(Boolean.TRUE);

    boolean actual = service.isProductExist(productName);

    assertTrue("Returned object doesn't match", actual);

    verify(productRepository).existsByName(productName);
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  public void isProductExist_false() {

    String productName = "";
    when(productRepository.existsByName(productName)).thenReturn(Boolean.FALSE);

    boolean actual = service.isProductExist(productName);

    assertFalse("Returned object doesn't match", actual);

    verify(productRepository).existsByName(productName);
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  public void saveProduct() {

    String productName = "";
    when(product.getName()).thenReturn(productName);
    when(productRepository.save(product)).thenReturn(null);
    when(service.isProductExist(productName)).thenReturn(Boolean.FALSE);

    boolean actual = service.saveProduct(product);

    assertFalse("Returned object doesn't match", actual);

    verify(productRepository).save(product);
    verify(productRepository).existsByName(productName);
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  public void saveProduct_true() {

    String productName = "";
    when(product.getName()).thenReturn(productName);
    when(productRepository.save(product)).thenReturn(product);
    when(service.isProductExist(productName)).thenReturn(Boolean.TRUE);

    boolean actual = service.saveProduct(product);

    assertTrue("Returned object doesn't match", actual);

    verify(productRepository).save(product);
    verify(productRepository).existsByName(productName);
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  public void deleteProductById() {

    Long productId = 1L;

    service.deleteProductById(productId);

    verify(productRepository).deleteById(productId);
    verifyNoMoreInteractions(productRepository);
  }
}
