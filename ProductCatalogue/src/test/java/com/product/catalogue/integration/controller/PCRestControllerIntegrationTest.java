package com.product.catalogue.integration.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.catalogue.ProductCatalogueApplication;
import com.product.catalogue.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = ProductCatalogueApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties")
public class PCRestControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;


	@Before
	public void setUp() {
		
		
	}
	
	@After
	public void clean() {
	
		
	}
	
	
	@Test
	public void getProductByIDIntTest() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/pc/product?productId=1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		//System.out.println(result.getResponse().getContentAsString());
		String expected = "{\"productId\":1,\"productName\":\"Shoes1\",\"productDesc\":\"Shoes for sale\",\"productCategory\":\"Shoes\",\"price\":100.2,\"color\":\"Black\",\"material\":\"Leather\"}";
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);		
	}

	@Test
	public void getAllProductsIntTest() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pc/products")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder)
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].productName", is("Shoes1")))
		.andExpect(jsonPath("$[0].productCategory", is("Shoes")))
		.andExpect(jsonPath("$[0].productDesc", is("Shoes for sale")))
		.andExpect(jsonPath("$[1].productName", is("Book1")))
		.andExpect(jsonPath("$[1].productCategory", is("Books")))
		.andExpect(jsonPath("$[1].productDesc", is("Book1 for sale")));

		
	}

	
	@Test
	public void getProductsOnBookCategoyIntTest() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pc/products/Books")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder)
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].productName", is("Book1")))
			.andExpect(jsonPath("$[0].productCategory", is("Books")))
			.andExpect(jsonPath("$[0].productDesc", is("Book1 for sale")))
			.andExpect(jsonPath("$[1].productName", is("Book2")))
			.andExpect(jsonPath("$[1].productCategory", is("Books")))
			.andExpect(jsonPath("$[1].productDesc", is("Book2 for sale")));

		
	}

	@Test
	public void getProductsOnShoeCategoyIntTest() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pc/products/Shoes")
				.accept(MediaType.APPLICATION_JSON);

		//Two Objects retured for category Shoes.
		mockMvc.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].productName", is("Shoes1")))
				.andExpect(jsonPath("$[0].productCategory", is("Shoes")))
				.andExpect(jsonPath("$[0].productDesc", is("Shoes for sale")))
				.andExpect(jsonPath("$[1].productName", is("Shoes2")))
				.andExpect(jsonPath("$[1].productCategory", is("Shoes")))
				.andExpect(jsonPath("$[1].productDesc", is("Shoes for sale")));

		
			
		
	}

	@Test
	public void getProductsOnDescIntTest() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pc/product/Shoes")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].productName", is("Shoes1")))
				.andExpect(jsonPath("$[0].productCategory", is("Shoes")))
				.andExpect(jsonPath("$[0].productDesc", is("Shoes for sale")))
				.andExpect(jsonPath("$[1].productName", is("Shoes2")))
				.andExpect(jsonPath("$[1].productCategory", is("Shoes")))
				.andExpect(jsonPath("$[1].productDesc", is("Shoes for sale")));

		
		
	}	
	
	@Test 
	public void addNewProductIntTest() throws Exception {
		
		String insertProductJson = "{\"productId\":null,\"productName\":\"Book_3\",\"productDesc\":\"Books\",\"productCategory\":\"Books\",\"price\":16.34,\"color\":\"Black\",\"material\":\"Paper\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/pc/product")
				.accept(MediaType.APPLICATION_JSON)
				.content(insertProductJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		//Using Jackson ObjectMapper to convert JSON to Object and comapre the attributes.
		Product p1 = new ObjectMapper().readValue(response.getContentAsString(), Product.class);
		assertEquals("Book_3", p1.getProductName());
		assertEquals("Books", p1.getProductDesc());
		assertEquals("Books", p1.getProductCategory());
		assertEquals(16.34, p1.getPrice(), 0.1);
		assertEquals("Paper", p1.getMaterial());
		assertEquals("Black", p1.getColor());
		
		
		
	}
	
	@Test 
	public void addNewProductWithNullObjectIntTest() throws Exception {
		
		String insertProductJson = "";

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/pc/product")
				.accept(MediaType.APPLICATION_JSON)
				.content(insertProductJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		assertEquals("", response.getContentAsString());

	}
	
	
	
	@Test 
	public void updateProductIntTest() throws Exception {
		
		String insertProductJson = "{\"productId\":5,\"productName\":\"Book_3\",\"productDesc\":\"Books\",\"productCategory\":\"Books\",\"price\":16.34,\"color\":\"Paper\",\"material\":\"Black\"}";
		
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
	public void deleteProductIntTest() throws Exception {
		
		String insertProductJson = "{\"productId\":5,\"productName\":\"Book_3\",\"productDesc\":\"Books\",\"productCategory\":\"Books\",\"price\":16.34,\"color\":\"Paper\",\"material\":\"Black\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/pc/product/5")
				.accept(MediaType.APPLICATION_JSON)
				.content(insertProductJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}


	
	
	

}
