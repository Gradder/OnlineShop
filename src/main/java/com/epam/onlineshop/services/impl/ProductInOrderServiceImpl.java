package com.epam.onlineshop.services.impl;

import com.epam.onlineshop.entities.Order;
import com.epam.onlineshop.entities.Product;
import com.epam.onlineshop.entities.ProductInOrder;
import com.epam.onlineshop.entities.User;
import com.epam.onlineshop.repository.OrderRepository;
import com.epam.onlineshop.repository.ProductInOrderRepository;
import com.epam.onlineshop.repository.ProductRepository;
import com.epam.onlineshop.services.ProductInOrderService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.epam.onlineshop.entities.Status.NEW;
import static com.epam.onlineshop.entities.Status.PREPAID;

@Service
@RequiredArgsConstructor
public class ProductInOrderServiceImpl implements ProductInOrderService {

    private static final String FOR_USER = "for user ";
    private final ProductRepository productRepository;
    private final ProductInOrderRepository productInOrderRepository;
    private final OrderRepository orderRepository;
    private static final Logger logger = Logger.getLogger(ProductInOrderServiceImpl.class);

    @Override
    public List<ProductInOrder> findAllNewOrderByUser(User user) {
        return productInOrderRepository.findAllNewOrderByUser(user);
    }

    @Override
    public List<ProductInOrder> findAllOrderedByUser(User user) {
        return productInOrderRepository.findAllOrderedByUser(user);
    }

    @Transactional
    @Override
    public ProductInOrder addOrderInCart(Long productId, User user) {
        Optional<ProductInOrder> optionalProductInOrder = productInOrderRepository.findOneOrderInCartByUserAndProductId(
            productId, user);
        if (optionalProductInOrder.isPresent()) {
            ProductInOrder productInOrder = optionalProductInOrder.get();
            productInOrder.setQuantity(productInOrder.getQuantity() + 1);
            return productInOrderRepository.save(productInOrder);
        } else {
            Product productFromCatalog = productRepository.getOne(productId);
            Order orderInCart = orderRepository.getOneNewOrderByUser(user);
            if (orderInCart == null){
                orderInCart = orderRepository.save(Order.builder()
                                                        .status(NEW)
                                                        .user(user)
                                                        .build());
            }
            logger.info("Product " +productFromCatalog.getName() + " was added in cart by username(" + user.getUsername() + ")");
            return productInOrderRepository.save(ProductInOrder.builder().order(orderInCart)
                                                        .product(productFromCatalog)
                                                        .quantity(1)
                                                        .build());
        }
    }

    @Override
    public void deleteById(Long id) {
        productInOrderRepository.deleteById(id);
        logger.info("Product was deleted in cart.");
    }

    @Override
    public void incrementCount(Long id) {
        Optional<ProductInOrder> optionalProduct = productInOrderRepository.findById(id);
        if (optionalProduct.isPresent()) {
            ProductInOrder productInOrder = optionalProduct.get();
            productInOrder.setQuantity(productInOrder.getQuantity() + 1);
            productInOrderRepository.save(productInOrder);
            logger.info("Quantity of product(" + productInOrder.getProduct().getName() + ") was incremented" +
                    FOR_USER + productInOrder.getOrder().getUser().getUsername() + " in cart.");
        } else{
            logger.warn("Product didn't find!");
        }
    }

    @Override
    public void decrementCount(Long id) {
        Optional<ProductInOrder> optionalProduct = productInOrderRepository.findById(id);
        if (optionalProduct.isPresent()) {
            ProductInOrder productInOrder = optionalProduct.get();
            productInOrder.setQuantity(productInOrder.getQuantity() - 1);
            productInOrderRepository.save(productInOrder);
            logger.info("Quantity of product(" + productInOrder.getProduct().getName() + ") was decremented" +
                FOR_USER + productInOrder.getOrder().getUser().getUsername() + " in cart.");
        } else{
            logger.warn("Product didn't find!");
        }
    }

    @Override
    public Integer getQuantityById(Long id) {
        Optional<ProductInOrder> optionalProduct = productInOrderRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get().getQuantity();
        } else{
            return 0;
        }
    }

    @Override
    public void makeOrder(User user) {
        List<ProductInOrder> orders = productInOrderRepository.findAllNewOrderByUser(user);
        for (ProductInOrder product : orders) {
            product.getOrder().setStatus(PREPAID);
        }
        productInOrderRepository.saveAll(orders);
        logger.info("Made order in status PREPAID " +
                FOR_USER + user.getUsername() + "");
    }

    @Override
    public List<ProductInOrder> getProductsFromThisOrder(Long id) {
        return productInOrderRepository.findByOrderId(id);
    }

    @Override
    public List<ProductInOrder> saveProductsInOrder(List<ProductInOrder> productsInOrder) {
        for (ProductInOrder productInOrder : productsInOrder) {
            productInOrderRepository.save(productInOrder);
        }

        return productsInOrder;
    }
}
