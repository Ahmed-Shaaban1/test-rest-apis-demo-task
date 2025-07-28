package tests;

import config.ConfigReader;
import models.Authors;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ApiClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;

public class AuthorTests {

	private ApiClient apiClient;
	private final String authorsEndpoint = "/api/v1/Authors";

	@BeforeClass
	public void setup() {
		apiClient = new ApiClient(ConfigReader.getBaseUrl());
	}

	@Test
	@Description("verify retrieving all authors")
	public void testGetAllAuthors() {
		Response response = apiClient.get(authorsEndpoint);
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Test
	@Description("verify retrieving author by BookId")
	public void testGetAuthorById_HappyPath() {
		Response response = apiClient.get(authorsEndpoint + "/authors/books/1");
		Assert.assertEquals(response.statusCode(), 200);
		List<Integer> ids = response.jsonPath().getList("id");
		Assert.assertTrue(ids.contains(1));
	}

	@Test
	@Description("verify retrieving author by Id")
	public void testGetAuthorByBookId_HappyPath() {
		Response response = apiClient.get(authorsEndpoint + "/1");
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("id"), 1);

	}

	@Test
	@Description("verify retrieving author by not found id")
	public void testGetAuthorById_NotFound() {
		Response response = apiClient.get(authorsEndpoint + "/99999");
		Assert.assertEquals(response.statusCode(), 404);
	}

	@Test
	@Description("verify creating author")
	public void testCreateAuthor() {
		Authors author = new Authors();
		author.setId(321);
		author.setIdBook("1");
		author.setFirstName("John");
		author.setLastName("Doe");

		Response response = apiClient.post(authorsEndpoint, author);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("id"), 321);
	}

	@Test
	@Description("verify creating author with invalid payload")
	public void testCreateAuthor_InvalidPayload() {
		String invalidJson = "{ \"firstName\": 123, \"lastName\": true }";
		Response response = apiClient.post(authorsEndpoint, invalidJson);
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	@Description("verify updating author")
	public void testUpdateAuthor() {
		Authors updated = new Authors();
		updated.setId(321);
		updated.setIdBook("1");
		updated.setFirstName("Jane");
		updated.setLastName("Doe");

		Response response = apiClient.put(authorsEndpoint + "/321", updated);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("firstName"), "Jane");
	}

	@Test
	@Description("verify updating not found author")
	public void testUpdateAuthor_NotFound() {
		Authors updated = new Authors();
		updated.setId(99999);
		updated.setIdBook("1");
		updated.setFirstName("Ghost");
		updated.setLastName("Writer");

		Response response = apiClient.put(authorsEndpoint + "/99999", updated);
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Test
	@Description("verify deleting author")
	public void testDeleteAuthor() {
		Response response = apiClient.delete(authorsEndpoint + "/321");
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Test
	@Description("verify deleting not found author")
	public void testDeleteAuthor_NotFound() {
		Response response = apiClient.delete(authorsEndpoint + "/321");
		Assert.assertEquals(response.statusCode(), 200);
	}
}
