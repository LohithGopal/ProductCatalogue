package com.product.catalogue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.catalogue.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>  {
	public List<Product> findAllByOrderByProductIdAsc();
	
	public List<Product> findByProductCategoryOrderByProductIdAsc(String category);
	
	@Query("Select p from Product p where p.productDesc like %:productDesc% ")
    List<Product> findByProductDesc(@Param("productDesc") String productDesc);


}
