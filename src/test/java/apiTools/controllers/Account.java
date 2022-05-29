package apiTools.controllers;

import apiTools.helpers.spec.RequestSpec;
import apiTools.helpers.spec.ResponseSpec;
import apiTools.models.request.LoginRq;
import apiTools.models.response.LoginRs;
import apiTools.models.response.TokenRs;
import apiTools.models.response.UserInfoRs;

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

    public Account() {
        requestSpec = new RequestSpec();
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

    public TokenRs generateToken(LoginRq loginRq) {
        return given()
            .spec(requestSpec.postRequestSpecification())
            .body(loginRq)
            .when()
            .post("/Account/v1/GenerateToken")
            .then()
            .spec(responseSpec.postResponseSpecification())
            .extract().jsonPath().getObject("", TokenRs.class);
    }

    public LoginRs login(LoginRq loginRq) {
        return given()
            .spec(requestSpec.postRequestSpecification())
            .body(loginRq)
            .when()
            .post("/Account/v1/Login")
            .then()
            .spec(responseSpec.postResponseSpecification())
            .extract().jsonPath().getObject("", LoginRs.class);
    }
}
