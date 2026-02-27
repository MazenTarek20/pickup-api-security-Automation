import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreatePickUpSecurity {

    @Test
    public void RequestWithoutToken(){
        RestAssured.baseURI = "https://stg-app.bosta.co";

        given()
                .contentType("application/json")
                .body("{}")
             .when()
                .post("/api/v2/pickups")
             .then()
                .statusCode(401);
    }

    @Test
    public void RejectPickUpForAnotherBusiness(){
        RestAssured.baseURI = "https://stg-app.bosta.co";

        given()
                .header("Authorization" , "VALID_TOKEN")
                .contentType("application/json")
                .body("""
                           {
                              "businessLocationId": "ANOTHER_BUSINESS_ID",
                              "numberOfParcels": "3"
                           }
                           """)
                .when()
                .post("/api/v2/pickups")
                .then()
                .statusCode(403);
    }

    @Test
    public void RejectZeroParcels() {

        RestAssured.baseURI = "https://stg-app.bosta.co";

        given()
                .header("Authorization", "VALID_TOKEN")
                .contentType("application/json")
                .body("""
            {
              "businessLocationId": "VALID_ID",
              "numberOfParcels": "0"
            }
        """)
                .when()
                .post("/api/v2/pickups")
                .then()
                .statusCode(400);
    }




}
