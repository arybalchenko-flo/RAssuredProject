import io.cucumber.java.Before;
import io.restassured.RestAssured;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseAPI {
    public void getRequestCheckStatusCode(String url, int code,Map<String, ?> parameters) {
    given().log().headers().log().all()
            .headers(parameters)
            .contentType("application/json\r\n")
            .when()
            .get(url)
            .then()
            .log().body().log().all()
            .statusCode(code);
}
}
