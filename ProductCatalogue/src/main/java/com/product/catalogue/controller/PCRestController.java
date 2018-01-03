package com.product.catalogue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.catalogue.exception.ProductNotFoundException;
import com.product.catalogue.model.Product;
import com.product.catalogue.service.ProductService;

@RestController
@RequestMapping("/pc")
@CrossOrigin(origins="http://localhost:4200", allowedHeaders="*")
public class PCRestController {

	@Autowired
	ProductService productService;
	
	private List<Product> products = null;
	private Product product = null;
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() {
		try {
			products = productService.getProducts(); 
			return new ResponseEntity<List<Product>>(products, HttpStatus.OK);		
		} catch (ProductNotFoundException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
		} 
	}
	
	@GetMapping("/products/{category}")
	public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category){
		
		try {
			products = productService.getProductsByCategory(category); 
			return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
		}
			
	}
	
	@GetMapping(value="/product")
	public ResponseEntity<Product> getProductsByID(@RequestParam Long productId){
	
		try {
			product = productService.getProductsByID(productId);
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}

		
	
	}
	
	@GetMapping("/product/{description}")
	public ResponseEntity<List<Product>> getProductsByDesc(@PathVariable String description){
		
		try {
			products = productService.getProductsByDesc(description); 
			return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping("/product")
	public ResponseEntity<Product> addNewProduct(@RequestBody Product newProduct) {
		
		try {
			product = productService.updateProduct(newProduct); 
			return new ResponseEntity<Product>(product, HttpStatus.CREATED);
		} catch (ProductNotFoundException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/product")
	public ResponseEntity<Product> updateProduct(@RequestBody Product newProduct) {
		
		try {
			product = productService.updateProduct(newProduct); 
			return new ResponseEntity<Product>(product, HttpStatus.CREATED);
		} catch (ProductNotFoundException | JpaSystemException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
		

	}
	
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<Boolean> deleteProduct(@PathVariable Long productId) {
		ResponseEntity<Boolean> response;
		 boolean status = productService.deleteProduct(productId);
		 if(status == false) {
			 response = new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
		 }else {
			 response = new ResponseEntity<Boolean>(HttpStatus.OK);
		 }
		 
		 return response;
	}

	

	
}
