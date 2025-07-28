package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);
    private final String baseUrl;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
    }

    public Response get(String endpoint) {
        logger.info("GET request to: {}", endpoint);
        Response response = given().log().all().get(endpoint);
        logger.info("Response: {}", response.asPrettyString());
        return response;
    }

    public Response post(String endpoint, Object body) {
        logger.info("POST request to: {}", endpoint);
        logger.info("Request Body: {}", body);
        Response response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .log().all()
                .post(endpoint);
        logger.info("Response: {}", response.asPrettyString());
        return response;
    }

    public Response put(String endpoint, Object body) {
        logger.info("PUT request to: {}", endpoint);
        logger.info("Request Body: {}", body);
        Response response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .log().all()
                .put(endpoint);
        logger.info("Response: {}", response.asPrettyString());
        return response;
    }

    public Response delete(String endpoint) {
        logger.info("DELETE request to: {}", endpoint);
        Response response = given().log().all().delete(endpoint);
        logger.info("Response: {}", response.asPrettyString());
        return response;
    }
}