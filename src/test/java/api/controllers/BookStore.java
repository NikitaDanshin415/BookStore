package api.controllers;

import api.helpers.spec.RequestSpec;
import api.helpers.spec.ResponseSpec;
import api.models.request.AddListOfBooksRq;
import api.models.request.DeleteBookRq;
import api.models.response.BookRs;

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
