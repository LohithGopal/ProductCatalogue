package com.product.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.catalogue.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
