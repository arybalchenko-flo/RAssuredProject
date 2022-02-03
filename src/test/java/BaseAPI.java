import io.cucumber.java.Before;
import io.restassured.RestAssured;

import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BaseAPI {
    public void getRequestCheckStatusCode(String url, int code,Map<String, ?> header, Map<String, ?> params)  {
    given()/*.log().headers().log().all()*/
            .headers(header)
            .contentType("application/json\r\n")
            .when()
            .get(url)
            .then()
/*            .log().body().log().all()*/
            .statusCode(code);
}
    public void propertyGet() {
        Paths.getPropertiesInstance();
    }
}
