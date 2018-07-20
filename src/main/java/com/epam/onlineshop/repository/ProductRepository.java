package com.epam.onlineshop.repository;

import com.epam.onlineshop.entities.Category;
import com.epam.onlineshop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);
    Optional<Product> findById(Long id);
    @Query(value = "SELECT a FROM Product as a where a.category = :category",
    countQuery = "SELECT count(a) from Product as a where a.category = :category")
    Page<Product> findAllByCategoryAndPageable(@Param("category") Category category, Pageable page);

    @Query("SELECT count(a) from Product as a where a.category = :category")
    int findAllByCategory(@Param("category") Category category);

    @Query("SELECT count(a) from Product as a")
    int getCountAll();

    @Query(value = "SELECT a FROM Product as a ",
            countQuery = "SELECT count(a) from Product as a")
    Page<Product> findAllWithPageable(Pageable page);
}
