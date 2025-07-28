package tests;

import config.ConfigReader;
import models.Books;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ApiClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;

public class BooksTests {

	private ApiClient apiClient;
	private final String booksEndpoint = "/api/v1/Books";

	@BeforeClass
	public void setup() {
		apiClient = new ApiClient(ConfigReader.getBaseUrl());
	}

	@Test
	@Description("verify retrieving all books")
	public void testGetAllBooks_HappyPath() {
		Response response = apiClient.get(booksEndpoint);
		Assert.assertEquals(response.statusCode(), 200);
//		Assert.assertTrue(response.jsonPath().getList("id").size() > 0);
	}

	@Test
	@Description("verify retrieving a book by id")
	public void testGetBookById_HappyPath() {
		Response response = apiClient.get(booksEndpoint + "/1");
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("id"), 1);
	}

	@Test
	@Description("verify retrieving a book with a not found id")
	public void testGetBookById_NotFound() {
		Response response = apiClient.get(booksEndpoint + "/99999");
		Assert.assertEquals(response.statusCode(), 404);
	}

	@Test
	@Description("verify creating book")
	public void testCreateBook_HappyPath() {
		
		int dynamicId = (int)(System.currentTimeMillis() / 1000);
		
		Books book = new Books();
		book.setId(dynamicId);
		book.setTitle("Test Book");
		book.setDescription("This is a test book.");
		book.setPageCount(100);
		book.setExcerpt("Excerpt here");
		book.setPublishDate("2025-07-25T00:00:00Z");

		Response response = apiClient.post(booksEndpoint, book);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("id"), dynamicId);
	}

	@Test
	@Description("verify creating a book with invalid payload")
	public void testCreateBook_InvalidPayload() {
		String invalidJson = "{ \"title\": 123, \"pageCount\": \"invalid\" }";
		Response response = apiClient.post(booksEndpoint, invalidJson);
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	@Description("verify updating a book ")
	public void testUpdateBook_HappyPath() {
		Books updated = new Books();
		updated.setId(12);
		updated.setTitle("Updated Book");
		updated.setDescription("Updated description");
		updated.setPageCount(120);
		updated.setExcerpt("Updated excerpt");
		updated.setPublishDate("2025-07-25T00:00:00Z");

		Response response = apiClient.put(booksEndpoint + "/12345", updated);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("title"), "Updated Book");
	}

	@Test
	@Description("verify updating a not found book")
	public void testUpdateBook_NotFound() {
		Books updated = new Books();
		updated.setId(99999);
		updated.setTitle("Nonexistent");
		updated.setDescription("No record");
		updated.setPageCount(1);
		updated.setExcerpt("Edge case");
		updated.setPublishDate("2025-07-25T00:00:00Z");

		Response response = apiClient.put(booksEndpoint + "/99999", updated);
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Test(description = "verify deleting a book")
	public void testDeleteBook_HappyPath() {
		Response response = apiClient.delete(booksEndpoint + "/12");
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Test(description = "verify deleting a not found book")
	public void testDeleteBook_NotFound() {
		Response response = apiClient.delete(booksEndpoint + "/12345");
		Assert.assertEquals(response.statusCode(), 200);
	}
}