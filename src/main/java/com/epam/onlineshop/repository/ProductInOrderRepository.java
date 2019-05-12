package com.epam.onlineshop.repository;

import com.epam.onlineshop.entities.ProductInOrder;
import com.epam.onlineshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {

    @Query("select orderInCart " +
            "from ProductInOrder as orderInCart " +
            "where orderInCart.order.user = :user " +
            "and orderInCart.order.status = 'NEW'")
    List<ProductInOrder> findAllNewOrderByUser(@Param("user") User user);

    @Query("select a from ProductInOrder as a where a.order.user = :user and (a.order.status = 'PREPAID' or a.order.status = 'DELIVERED')")
    List<ProductInOrder> findAllOrderedByUser(@Param("user") User user);

    @Query("select a from ProductInOrder as a where a.order.user = :user and a.order.status = 'NEW' and a.product.id = :product_id")
    Optional<ProductInOrder> findOneOrderInCartByUserAndProductId(@Param("product_id") Long productId, @Param("user") User user);

    Optional<ProductInOrder> findById(@Param("id") Long id);

    List<ProductInOrder> findByOrderId (Long id);
}
