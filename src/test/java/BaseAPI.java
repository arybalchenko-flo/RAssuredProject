import io.cucumber.java.Before;
import io.restassured.RestAssured;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BaseAPI {
    public static JSONObject takeJsonToSend(String jsonFileName) {
        File file = new File(Paths.pathToJsons() + jsonFileName + ".json");
        try {
            return (JSONObject) readJsonSimpleDemo(file);
        } catch (Exception e) {
            System.out.println("Exception in take json to send");
        }
        return null;
    }
    private static Object readJsonSimpleDemo(File file) throws Exception {
        FileReader reader = new FileReader(file);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }
    public void getRequestCheckStatusCode(String url, int code,Map<String, ?> header, Map<String, ?> params)  {
    given().log().headers().log().all()
            .headers(header)
            .contentType("application/json\r\n")
            .when()
            .get(url)
            .then()
/*            .log().body().log().all()*/
            .statusCode(code);
}
    public void sendPOSTRequestSendJsonCheckStatusCode(String url, int code, Map<String, ?> header, Map<String, ?> params, JSONObject jsonObject)  {
        given().log().headers().log().all()
                .headers(header)
                .contentType("application/json\r\n")
                .body(jsonObject.toString())
                .when()
                .get(url)
                .then()
/*            .log().body().log().all()*/
                .statusCode(code);
    }
}

