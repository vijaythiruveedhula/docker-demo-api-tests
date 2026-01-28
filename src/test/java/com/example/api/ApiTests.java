package com.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(description = "GET /posts/1 returns post with id 1")
    public void testGetPost() {
        given()
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1));
    }

    @Test(description = "POST /posts creates a new post")
    public void testCreatePost() {
        String payload = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";

        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .body("title", equalTo("foo"));
    }

    @Test(description = "PUT /posts/1 updates the post")
    public void testUpdatePost() {
        String payload = "{\"id\":1,\"title\":\"updated\",\"body\":\"bar\",\"userId\":1}";

        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .put("/posts/1")
        .then()
            .statusCode(200)
            .body("title", equalTo("updated"));
    }

    @Test(description = "DELETE /posts/1 deletes the post")
    public void testDeletePost() {
        given()
        .when()
            .delete("/posts/1")
        .then()
            .statusCode(200);
    }
}
