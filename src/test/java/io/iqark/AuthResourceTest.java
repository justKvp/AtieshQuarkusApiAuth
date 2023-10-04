package io.iqark;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AuthResourceTest {

    @Test
    public void testGetAccountEndpoint() {
        given()
          .when().get("/auth/token")
          .then()
             .statusCode(500);
    }

    @Test
    public void testVerifyAccountEndpoint() {
        given()
                .when().post("/auth/getAccount/1")
                .then()
                .statusCode(405);
    }

}