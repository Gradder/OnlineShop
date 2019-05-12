package com.epam.onlineshop.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "image_url")
    private String imageLink;

    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    private List<ProductInOrder> productInOrderList;
}
