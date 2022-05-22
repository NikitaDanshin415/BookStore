package api.controllers;

import api.helpers.spec.RequestSpec;
import api.helpers.spec.ResponseSpec;
import api.models.request.LoginRq;
import api.models.response.LoginRs;
import api.models.response.TokenRs;

import static io.restassured.RestAssured.given;

/**
 * Класс для авторизации в системе.
 */
public class AccountAuth {
    RequestSpec requestSpec;
    ResponseSpec responseSpec;

    public AccountAuth() {
        requestSpec = new RequestSpec();
        responseSpec = new ResponseSpec();
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
