package com.product.catalogue.unit.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.product.catalogue.controller.PCRestController;
import com.product.catalogue.exception.ProductNotFoundException;
import com.product.catalogue.model.Product;
import com.product.catalogue.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=PCRestController.class, secure=false)
public class PCRestControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;
	
	private Product shoeProduct;
	private Product shoe_1_product;
	private Product bookProduct;
	private Product book_1_product;
	private Product insertProduct;
	private String insertProductJson;
	private List<Product> products = new ArrayList<Product>();;
	private List<Product> bookProducts = new ArrayList<Product>();;
	private List<Product> shoeProducts = new ArrayList<Product>();;
	
	
	@Before
	public void setUp(){
		shoeProduct = new Product(1L, "Shoe_1", "Shoes", "Shoes", (float) 12.34, "Leather", "Black");
		bookProduct = new Product(2L, "Book_1", "Books", "Books", (float) 13.34, "Paper", "Red");
		shoe_1_product = new Product(3L, "Shoe_2", "Shoes", "Shoes", (float) 14.34, "Leather", "Black");
		book_1_product = new Product(4L, "Book_2", "Books", "Books", (float) 15.34, "Paper", "Red");
		insertProduct = new Product(5L, "Book_3", "Books", "Books", (float) 16.34, "Paper", "Black");
		
		
		products.add(shoeProduct);
		products.add(bookProduct);
		
		bookProducts.add(bookProduct);
		bookProducts.add(book_1_product);
		
		shoeProducts.add(shoeProduct);
		shoeProducts.add(shoe_1_product);
		
	}
	
	@After
	public void clean() {
	
		products.clear();
		bookProducts.clear();
		shoeProducts.clear();
		
		
	}
	
	
	@Test
	public void getProductByIDTest() throws Exception {
		
		Mockito.when(productService.getProductsByID(Mockito.anyLong()))
				.thenReturn(shoeProduct);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/pc/product?productId=1").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		//System.out.println(result.getResponse().getContentAsString());
		String expected = "{\"productId\":1,\"productName\":\"Shoe_1\",\"productDesc\":\"Shoes\",\"productCategory\":\"Shoes\",\"price\":12.34,\"color\":\"Leather\",\"material\":\"Black\"}";
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);		
	}

	@Test
	public void getProductByIDDataNotFoundExceptionTest() throws Exception {
		
		ProductNotFoundException pnf = new ProductNotFoundException("Product Not Found for 1232");
		Mockito.when(productService.getProductsByID(Mockito.anyLong()))
				.thenThrow(pnf);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/pc/product?productId=1").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(HttpStatus.NOT_FOUND.value(),  result.getResponse().getStatus());
		
	}
	
	@Test
	public void getAllProductsTest() throws Exception {
		Mockito.when(productService.getProducts())
		.thenReturn(products);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pc/products")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		//System.out.println(result.getResponse().getContentAsString());
		String expected = "[{\"productId\":1,\"productName\":\"Shoe_1\",\"productDesc\":\"Shoes\",\"productCategory\":\"Shoes\",\"price\":12.34,\"color\":\"Leather\",\"material\":\"Black\"},{\"productId\":2,\"productName\":\"Book_1\",\"productDesc\":\"Books\",\"productCategory\":\"Books\",\"price\":13.34,\"color\":\"Paper\",\"material\":\"Red\"}]";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getAllProductsForProductNotFoundExceptionTest() throws Exception {
		
		ProductNotFoundException pnf = new ProductNotFoundException("No Products found in the DB");
		Mockito.when(productService.getProducts())
		.thenThrow(pnf);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pc/products")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.NOT_FOUND.value(),  result.getResponse().getStatus());
		
	}

	
	@Test
	public void getProductsOnBookCategoyTest() throws Exception {
		Mockito.when(productService.getProductsByCategory("Books"))
		.thenReturn(bookProducts);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pc/products/Books")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		//System.out.println(result.getResponse().getContentAsString());
		String expected = "[{\"productId\":2,\"productName\":\"Book_1\",\"productDesc\":\"Books\",\"productCategory\":\"Books\",\"price\":13.34,\"color\":\"Paper\",\"material\":\"Red\"},{\"productId\":4,\"productName\":\"Book_2\",\"productDesc\":\"Books\",\"productCategory\":\"Books\",\"price\":15.34,\"color\":\"Paper\",\"material\":\"Red\"}]";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void getProductsOnShoeCategoyTest() throws Exception {
		Mockito.when(productService.getProductsByCategory("Shoes"))
		.thenReturn(shoeProducts);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pc/products/Shoes")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		//System.out.println(result.getResponse().getContentAsString());
		String expected = "[{\"productId\":1,\"productName\":\"Shoe_1\",\"productDesc\":\"Shoes\",\"productCategory\":\"Shoes\",\"price\":12.34,\"color\":\"Leather\",\"material\":\"Black\"},{\"productId\":3,\"productName\":\"Shoe_2\",\"productDesc\":\"Shoes\",\"productCategory\":\"Shoes\",\"price\":14.34,\"color\":\"Leather\",\"material\":\"Black\"}]";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void getProductsOnDescTest() throws Exception {
		Mockito.when(productService.getProductsByDesc(Mockito.anyString()))
		.thenReturn(shoeProducts);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pc/product/Shoes")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		//System.out.println(result.getResponse().getContentAsString());
		String expected = "[{\"productId\":1,\"productName\":\"Shoe_1\",\"productDesc\":\"Shoes\",\"productCategory\":\"Shoes\",\"price\":12.34,\"color\":\"Leather\",\"material\":\"Black\"},{\"productId\":3,\"productName\":\"Shoe_2\",\"productDesc\":\"Shoes\",\"productCategory\":\"Shoes\",\"price\":14.34,\"color\":\"Leather\",\"material\":\"Black\"}]";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}	
	

	@Test
	public void getProductsOnDescForProductNotFoundExceptionTest() throws Exception {
		
		ProductNotFoundException pnf = new ProductNotFoundException("No Products found in the DB for this Description ");
		Mockito.when(productService.getProductsByDesc(Mockito.anyString()))
		.thenThrow(pnf);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pc/product/Shoes")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.NOT_FOUND.value(),  result.getResponse().getStatus());
	}	
	
	
	@Test 
	public void addNewProductTest() throws Exception {
		Mockito.when(productService.updateProduct(Mockito.any(Product.class)))
		.thenReturn(insertProduct);
		
		insertProductJson = "{\"productId\":5,\"productName\":\"Book_3\",\"productDesc\":\"Books\",\"productCategory\":\"Books\",\"price\":16.34,\"color\":\"Paper\",\"material\":\"Black\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/pc/product")
				.accept(MediaType.APPLICATION_JSON)
				.content(insertProductJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals(insertProductJson, response.getContentAsString());

	}
	
	@Test 
	public void addNewProductFOrProductNotFoundExceptionTest() throws Exception {
		
		ProductNotFoundException pnf = new ProductNotFoundException("Unable to Create/Update Product");
		Mockito.when(productService.updateProduct(Mockito.any(Product.class)))
		.thenThrow(pnf);
		
		insertProductJson = "{\"productId\":5,\"productName\":\"Book_3\",\"productDesc\":\"Books\",\"productCategory\":\"Books\",\"price\":16.34,\"color\":\"Paper\",\"material\":\"Black\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/pc/product")
				.accept(MediaType.APPLICATION_JSON)
				.content(insertProductJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(HttpStatus.NOT_FOUND.value(),  result.getResponse().getStatus());
	}
	
	
	
	@Test 
	public void updateProductTest() throws Exception {
		Mockito.when(productService.updateProduct(Mockito.any(Product.class)))
		.thenReturn(insertProduct);
		
		insertProductJson = "{\"productId\":5,\"productName\":\"Book_3\",\"productDesc\":\"Books\",\"productCategory\":\"Books\",\"price\":16.34,\"color\":\"Paper\",\"material\":\"Black\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/pc/product")
				.accept(MediaType.APPLICATION_JSON)
				.content(insertProductJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals(insertProductJson, response.getContentAsString());
	}
		

	@Test 
	public void deleteProduct() throws Exception {
		Mockito.when(productService.deleteProduct(Mockito.anyLong()))
		.thenReturn(true); //Delete is succesfull
		
		insertProductJson = "{\"productId\":5,\"productName\":\"Book_3\",\"productDesc\":\"Books\",\"productCategory\":\"Books\",\"price\":16.34,\"color\":\"Paper\",\"material\":\"Black\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/pc/product/5")
				.accept(MediaType.APPLICATION_JSON)
				.content(insertProductJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}

	@Test 
	public void deleteProductIsUnsuccesfull() throws Exception {
		Mockito.when(productService.deleteProduct(Mockito.anyLong()))
		.thenReturn(false); //Delete is unsuccesfull
		
		insertProductJson = "{\"productId\":5,\"productName\":\"Book_3\",\"productDesc\":\"Books\",\"productCategory\":\"Books\",\"price\":16.34,\"color\":\"Paper\",\"material\":\"Black\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/pc/product/5")
				.accept(MediaType.APPLICATION_JSON)
				.content(insertProductJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
		
	}
	
	
}
