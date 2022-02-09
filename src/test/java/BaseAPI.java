import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

import java.io.File;
import java.io.FileReader;
import java.util.*;

import static io.restassured.RestAssured.given;

public class BaseAPI {
    public Map<String, List> varsForArrays = new HashMap<>();
    public Map<String, String> vars = new HashMap<>();

    public static JSONObject takeJsonToSend(String jsonFileName) {
        File file = new File(Paths.pathToJsons() + jsonFileName + ".json");
        try {
            return (JSONObject) readJsonSimpleDemo(file);
        } catch (Exception e) {
            System.out.println("Exception in take json to send");
        }
        System.out.println(file);
        return null;
    }

    private static Object readJsonSimpleDemo(File file) throws Exception {
        FileReader reader = new FileReader(file);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

    public void getRequestCheckStatusCode(String url, int code, Map<String, ?> header, Map<String, ?> params) {
        String link=Paths.urlValue(url);
        given().log().headers().log().all()
                .headers(header)
                .contentType("application/json\r\n")
                .when()
                .get(link)
                .then()
                .log().body().log().all()
                .statusCode(code);
    }

    public void sendPOSTRequestSendJsonCheckStatusCode(String url, int code, Map<String, ?> header, Map<String, ?> params, JSONObject jsonObject) {
        String link=Paths.urlValue(url);
        given().log().headers().log().all()
                .headers(header)
                .contentType("application/json")
                .body(jsonObject.toString())
                .when()
                .post(link)
                .then()
                .log().body().log().all()
                .statusCode(code);
    }

    public void jsonPathGetRequestCheckStatusCode(String url, String valueFrom, String var, int code, Map<String, ?> header, Map<String, ?> params) {
        String link=Paths.urlValue(url);
        RequestSpecification requestSpecification = given().log().headers().log().all()
                .headers(header)
                .contentType("application/json\r\n");
        Response response = requestSpecification.when().get(link);
        response.then().log().body()
                .statusCode(code);
        String rbody = response.asString();
        JsonPath jsonPath = new JsonPath(rbody);
        String str = jsonPath.getString(valueFrom);
        str = str.replace("[", "").replace("]", "").replace("{", "").replace("}", "");
        String[] words = str.split(", ");
        List<String> valueOfKey = Arrays.asList(words);
        varsForArrays.put(var, valueOfKey);
        System.out.println(varsForArrays.get(var));
    }
}

