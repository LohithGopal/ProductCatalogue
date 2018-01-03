package com.product.catalogue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.catalogue.exception.ProductNotFoundException;
import com.product.catalogue.model.Product;
import com.product.catalogue.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	public List<Product> getProducts() throws ProductNotFoundException{
		
		List<Product> products =  productRepository.findAllByOrderByProductIdAsc();
		if (products.isEmpty()) {
			throw new ProductNotFoundException("No Products found in the DB");
		}
		
		return products;
	}

	public List<Product> getProductsByCategory(String category) throws ProductNotFoundException {
		
		List<Product> products =  productRepository.findByProductCategoryOrderByProductIdAsc(category);
		
		if (products.isEmpty()) {
			throw new ProductNotFoundException("No Products found in the DB for this category " + category);
		}
		
		return products;
	}

	public Product updateProduct(Product newProduct) throws ProductNotFoundException {
		
		Product product = productRepository.save(newProduct);
		
		if(product == null) {
			throw new ProductNotFoundException("Unable to Create/Update Product");
		}
		
		return product;
	
	}

	public boolean deleteProduct(Long productId) {
		 productRepository.delete(productId);
		 return true;
	}

	public List<Product> getProductsByDesc(String description) throws ProductNotFoundException {
		
		List<Product> products =  productRepository.findByProductDesc(description);
		if (products.isEmpty()) {
			throw new ProductNotFoundException("No Products found in the DB for this Description " + description);
		}
		
		return products;
	}

	public Product getProductsByID(Long productId) throws ProductNotFoundException {
		
		Product product = productRepository.findOne(productId);
		
		 if (product == null) {
			 throw new ProductNotFoundException("Product Not Found for "+ productId);
		 }
		 
		 return product;
			
	}
	
	
}
