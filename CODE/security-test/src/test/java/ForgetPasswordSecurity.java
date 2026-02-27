package security;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ForgetPasswordSecurity {

    @Test
    public void shouldRejectRequestWithoutToken() {
        RestAssured.baseURI = "https://stg-app.bosta.co";

        given()
                .contentType("application/json")
                .body("{\"email\":\"test@bosta.co\"}")
                .when()
                .post("/api/v2/users/forget-password")
                .then()
                .statusCode(401);
    }

    @Test
    public void shouldRejectInvalidEmailFormat() {
        RestAssured.baseURI = "https://stg-app.bosta.co";

        given()
                .header("Authorization", "VALID_TOKEN")
                .contentType("application/json")
                .body("{\"email\":\"invalid@@mail\"}")
                .when()
                .post("/api/v2/users/forget-password")
                .then()
                .statusCode(400);
    }

    @Test
    public void shouldAcceptValidEmail() {
        RestAssured.baseURI = "https://stg-app.bosta.co";

        given()
                .header("Authorization", "VALID_TOKEN")
                .contentType("application/json")
                .body("{\"email\":\"amira.mosa@bosta.co\"}")
                .when()
                .post("/api/v2/users/forget-password")
                .then()
                .statusCode(200);
    }
}
