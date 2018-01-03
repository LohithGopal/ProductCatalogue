package com.product.catalogue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.catalogue.model.Cart;
import com.product.catalogue.repository.CartRepository;

@Service
@Transactional
public class CartService {

	@Autowired
	CartRepository cartRepository;

	public List<Cart> getCarts() {
		
		return null;
	}

	public Cart updateCart(Cart catalogue) {
		
		return cartRepository.save(catalogue);
	}
	
}
