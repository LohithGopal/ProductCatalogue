package com.product.catalogue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.catalogue.model.Cart;
import com.product.catalogue.service.CartService;

@RestController
@RequestMapping("/pc")
@CrossOrigin(origins="http://localhost:4200", allowedHeaders="*")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@GetMapping("/cart")
	public List<Cart> getProducts() {
		return cartService.getCarts();
	}
	
	@PostMapping("/cart")
	public Cart addNewProduct(@RequestBody Cart catalogue) {
		return cartService.updateCart(catalogue);
	}
	
	
}
