package api.controllers;

import api.helpers.spec.RequestSpec;
import api.helpers.spec.ResponseSpec;
import api.models.response.UserInfoRs;

import static io.restassured.RestAssured.given;

/**
 * Класс для методов контроллера Account
 */
public class Account {
    RequestSpec requestSpec;
    ResponseSpec responseSpec;

    public Account(String token) {
        requestSpec = new RequestSpec(token);
        responseSpec = new ResponseSpec();
    }

    public UserInfoRs getUserInfo(String userId) {
        return given()
            .spec(requestSpec.getRequestSpecification())
            .when()
            .get("/Account/v1/User/" + userId)
            .then()
            .spec(responseSpec.getResponseSpecification())
            .extract().jsonPath().getObject("", UserInfoRs.class);
    }
}
