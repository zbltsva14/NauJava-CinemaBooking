package ru.zyablitseva.NauJava.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    void findMovies_WithoutAuth_RedirectsToLogin() {
        given()
                .redirects().follow(false)
                .when()
                .get("/api/movies/find?genre=Фантастика&ageRating=16+")
                .then()
                .statusCode(302);
    }

    @Test
    void findMovies_WithAuth_ReturnsOk() {
        String sessionId = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "admin")
                .formParam("password", "admin123")
                .redirects().follow(false)
                .when()
                .post("/login")
                .then()
                .statusCode(302)
                .extract()
                .sessionId();
        given()
                .sessionId(sessionId)
                .when()
                .get("/api/movies/find?genre=Фантастика&ageRating=16+")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    void findMovies_NonExistentParams_ReturnsOkWithEmptyList() {
        String sessionId = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "admin")
                .formParam("password", "admin123")
                .redirects().follow(false)
                .when()
                .post("/login")
                .then()
                .statusCode(302)
                .extract()
                .sessionId();
        given()
                .sessionId(sessionId)
                .when()
                .get("/api/movies/find?genre=NONEXISTENT&ageRating=999")
                .then()
                .statusCode(200);
    }
}