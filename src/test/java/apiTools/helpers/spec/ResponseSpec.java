package apiTools.helpers.spec;


import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpec {
    public ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.ALL)
            .build();
    }

    public ResponseSpecification deleteResponseSpecification() {
        return new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(LogDetail.ALL)
            .build();
    }

    public ResponseSpecification postResponseSpecification() {
        return new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.ALL)
            .build();
    }
    public ResponseSpecification addResponseSpecification() {
        return new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(LogDetail.ALL)
            .build();
    }


    public ResponseSpecification loginResponseSpecification() {
        return new ResponseSpecBuilder()
            .expectStatusCode(302)
            .log(LogDetail.ALL)
            .build();
    }
}