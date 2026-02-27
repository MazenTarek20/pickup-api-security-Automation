

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UpdateBankInfoSecurity {

    @Test
    public void shouldRejectRequestWithoutToken() {
        RestAssured.baseURI = "https://stg-app.bosta.co";

        given()
                .contentType("application/json")
                .body("{}")
                .when()
                .post("/api/v2/businesses/add-bank-info")
                .then()
                .statusCode(401);
    }

    @Test
    public void shouldRejectBankInfoForAnotherBusiness() {
        RestAssured.baseURI = "https://stg-app.bosta.co";

        given()
                .header("Authorization", "VALID_TOKEN")
                .contentType("application/json")
                .body("""
                {
                  "bankInfo": {
                    "beneficiaryName": "Hacker Test",
                    "ibanNumber": "EG0000000000000000",
                    "accountNumber": "1234567890",
                    "bankName": "Fake Bank"
                  },
                  "paymentInfoOtp": "123"
                }
            """)
                .when()
                .post("/api/v2/businesses/add-bank-info")
                .then()
                .statusCode(403);
    }

    @Test
    public void shouldRejectInvalidBankInfo() {
        RestAssured.baseURI = "https://stg-app.bosta.co";

        given()
                .header("Authorization", "VALID_TOKEN")
                .contentType("application/json")
                .body("""
                {
                  "bankInfo": {
                    "beneficiaryName": "",
                    "ibanNumber": "INVALID",
                    "accountNumber": "000",
                    "bankName": ""
                  },
                  "paymentInfoOtp": "123"
                }
            """)
                .when()
                .post("/api/v2/businesses/add-bank-info")
                .then()
                .statusCode(400);
    }
}
