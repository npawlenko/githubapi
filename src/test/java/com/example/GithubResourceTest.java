package com.example;

import com.example.github.resource.dto.ErrorResponse;
import com.example.github.resource.dto.RepositoryDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class GithubResourceTest {

	@Test
	void testGetNotForkedUserRepos_Success() {
		String userName = "npawlenko";

		Response response = given().pathParam("userName", userName)
			.when()
			.get("/api/github/repos/{userName}")
			.then()
			.statusCode(200)
			.extract()
			.response();

		List<RepositoryDTO> repositories = response.as(new TypeRef<>() {
		});

		assert repositories != null;
		assert repositories.stream()
			.allMatch(repo -> repo.branches() != null && !repo.branches().isEmpty());
	}

	@Test
	void testGetNotForkedUserRepos_UserNotFound() {
		String invalidUserName = "npawlenkodasdas";

		Response response = given().pathParam("userName", invalidUserName)
			.when()
			.get("/api/github/repos/{userName}")
			.then()
			.statusCode(404)
			.extract()
			.response();

		ErrorResponse errorResponse = response.as(new TypeRef<>() {});

		assert response.statusCode() == 404;
		assert errorResponse != null;
		assert errorResponse.status() == 404;
		assert errorResponse.message() != null;
		assert errorResponse.message().equals("Resource was not found");
	}
}