package io.iqark;

import io.iqark.tcauth.entity.Account;
import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.test.junit.QuarkusTest;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AuthResourceTest {

    @Test
    public void testGetAccountEndpoint() {
//        given()
//          .when().get("/auth/token")
//          .then()
//             .statusCode(200);

        System.out.println("TEST1 SAD");

        Account account = Account.findByUsername("TEST1");
        System.out.println(account.getUsername());
    }

//    @Test
//    public void testVerifyAccountEndpoint() {
//        given()
//                .when().post("/auth/getAccount/1")
//                .then()
//                .statusCode(405);
//    }

}