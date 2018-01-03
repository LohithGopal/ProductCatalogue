package com.product.catalogue.unit.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.product.catalogue.model.Product;
import com.product.catalogue.repository.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ProductRepositoryUnitTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ProductRepository productRepository;
	
	private Product p1;
	@Before
	public void setUp() {
		p1= new Product(null, "ShoeTest", "Shoes", "Shoes", (float) 12.34, "Leather", "Black");
		entityManager.persist(p1);
		entityManager.flush();
		
	}
	
	@After
	public void clean() {
		
	}
	
	@Test
	public void findAllByOrderByProductIdAscTest() {
		
		List<Product> products =  productRepository.findAllByOrderByProductIdAsc();
		
		//Asserting to 13(12 DBrecords + 1 from the above em.persist) records 
		//since there are few records already existing in the DB.
		assertEquals(13, products.size()); 
		
	}
	
	@Test
	public void findByProductCategoryOrderByProductIdAscTest() {
		
		List<Product> products =  productRepository.findByProductCategoryOrderByProductIdAsc("Shoes");
		
		assertEquals(4, products.size());
	
	}
	
	@Test
	public void findByNULLProductCategoryTest() {
		
		List<Product> products =  productRepository.findByProductCategoryOrderByProductIdAsc(null);
		
		assertEquals(0, products.size());
	
	}

	@Test
	public void saveTest() {
		
		Product product =  productRepository.save(p1);
		
		assertEquals("ShoeTest", product.getProductName());
	
	}
	
	@Test
	public void findOneTest() {

		Product product =  productRepository.findOne(p1.getProductId());
		
		assertEquals("ShoeTest", product.getProductName());
	
	}
	
	@Test
	public void deleteTest() {
		
		Product product =  productRepository.findOne(p1.getProductId());
		assertEquals("ShoeTest", product.getProductName());
		assertNotNull(product);
		
		productRepository.delete(p1.getProductId());
		
		product =  productRepository.findOne(p1.getProductId());
		assertNull(product);
		
	}
		
}
