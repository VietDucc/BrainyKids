package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WordApiTest {

	private final String JWT_TOKEN = System.getenv("JWT_TOKEN") != null ? System.getenv("JWT_TOKEN") : "";
	private final String BASE_URL = System.getenv("API_BASE_URL") != null ? System.getenv("API_BASE_URL") : "https://duc-spring.ngodat0103.live";

	@Test
	void testCoursesApiReturnsData() {
		Response response = RestAssured
				.given()
				.header("Authorization", "Bearer " + JWT_TOKEN)
				.get(BASE_URL + "/demo/api/courses");

		Assertions.assertEquals(200, response.getStatusCode());

		var dataList = response.getBody().jsonPath().getList("$");
		Assertions.assertFalse(dataList.isEmpty(), "API không trả về dữ liệu nào");
	}

	@Test
	void testUnitApiGetById() {
		Response response = RestAssured
				.given()
				.header("Authorization", "Bearer " + JWT_TOKEN)
				.get(BASE_URL + "/demo/api/units/1");

		Assertions.assertEquals(200, response.getStatusCode());

		var dataList = response.getBody().jsonPath().getList("$");
		Assertions.assertFalse(dataList.isEmpty(), "API không trả về dữ liệu nào");
	}
}
