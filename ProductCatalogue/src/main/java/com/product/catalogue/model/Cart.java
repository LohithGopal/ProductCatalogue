package com.product.catalogue.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CART")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catalogue_generator")
	@SequenceGenerator(name="catalogue_generator", sequenceName = "catalogue_seq", allocationSize=20 )
	private Long cartId;
	@Column
	private Long productId;
	@Column(nullable=false)
	private String quantity;
	
	
	public Long getCatalogueId() {
		return cartId;
	}
	public void setCatalogueId(Long catalogueId) {
		this.cartId = catalogueId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "Catalogue [catalogueId=" + cartId + ", productId=" + productId + ", quantity=" + quantity + "]";
	}
	
	
	
	
	
}
