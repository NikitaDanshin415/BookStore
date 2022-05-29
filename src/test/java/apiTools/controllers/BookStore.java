package apiTools.controllers;

import apiTools.helpers.spec.RequestSpec;
import apiTools.helpers.spec.ResponseSpec;
import apiTools.models.request.AddListOfBooksRq;
import apiTools.models.request.DeleteBookRq;
import apiTools.models.response.BookRs;

import static io.restassured.RestAssured.given;

/**
 * Класс для методов контроллера BookStore
 */
public class BookStore {
    RequestSpec requestSpec;
    ResponseSpec responseSpec;

    public BookStore(String token) {
        requestSpec = new RequestSpec(token);
        responseSpec = new ResponseSpec();
    }

    public BookRs[] books() {
        return given()
            .spec(requestSpec.getRequestSpecification())
            .when()
            .get("/BookStore/v1/Books")
            .then()
            .spec(responseSpec.getResponseSpecification())
            .extract().jsonPath().getObject("books", BookRs[].class);
    }

    public void book(AddListOfBooksRq rq) {
        given()
            .spec(requestSpec.postRequestSpecification())
            .body(rq)
            .when()
            .post("/BookStore/v1/Books")
            .then()
            .spec(responseSpec.addResponseSpecification());
    }

    public void deleteBook(DeleteBookRq rq) {
        given()
            .spec(requestSpec.postRequestSpecification())
            .body(rq)
            .when()
            .delete("/BookStore/v1/Book")
            .then()
            .spec(responseSpec.deleteResponseSpecification());
    }
}
