package api.helpers.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static api.helpers.listners.CustomAllureListner.withCustomTemplates;

public class RequestSpec {
    private String token;

    public RequestSpec() {
    }

    public RequestSpec(String token) {
        this.token = token;
    }

    public RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
            .addHeader("Authorization", token == null ? "" : "Bearer " + this.token)
            .log(LogDetail.ALL)
            .build();
    }

    public RequestSpecification postRequestSpecification() {
        return new RequestSpecBuilder()
            .addFilter(withCustomTemplates())
            .setContentType(ContentType.JSON)
            .addHeader("Authorization", token == null ? "" : "Bearer " + this.token)
            .log(LogDetail.ALL)
            .build();
    }
}
