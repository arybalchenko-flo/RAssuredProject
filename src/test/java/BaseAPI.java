import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.javacrumbs.jsonunit.core.Option;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.File;
import java.io.FileReader;
import java.util.*;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.*;

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
    public void getJsonStructureCheckStatusCodeCheck(String url, int code, Map<String, ?> header, Map<String, ?> params, String jsonFileName) {
        String link=Paths.urlValue(url);
        RequestSpecification requestSpecification = given().log().headers().log().all()
                .headers(header)
                .contentType("application/json\r\n");
        Response response = requestSpecification.when().get(link);
        response.then().log().body()
                .assertThat().statusCode(code);
        String test = response.getBody().asString();
        assertThatJson(test)
                .when(Option.IGNORING_VALUES)
                .isEqualTo(json(takeJsonToSend(jsonFileName)));
    }

    private JSONObject replaceJsonKey(String jsonFileName, String keyToDelete, String newKeyName) {
        JSONObject jsonObject = takeJsonToSend(jsonFileName);
        jsonObject.put(newKeyName, jsonObject.get(keyToDelete));
        jsonObject.remove(keyToDelete);
        System.out.println(jsonObject);
        return jsonObject;
    }
    public void getJsonStructureWithJsonKeyChangeAndCheckStatusCode(String url, String keyToDelete, String newKeyName, String jsonFileName, int code, Map<String, ?> header, Map<String, ?> params) {
        String link=Paths.urlValue(url);
        RequestSpecification requestSpecification = given().log().headers().log().all()
                .headers(header)
                .contentType("application/json\r\n");
        Response response = requestSpecification.when().get(link);
        response.then().log().body()
                .assertThat().statusCode(code);
        String test = response.getBody().asString();
        assertThatJson(test)
                .when(Option.IGNORING_VALUES)
                .isEqualTo(json(replaceJsonKey(jsonFileName, keyToDelete, newKeyName)));
    }

    private JSONObject replaceValueInJsonToString (String keyName, String value, JSONObject jsonObject) {
        jsonObject.put(keyName, value);
        return jsonObject;
    }
    private JSONObject replaceValueInJsonToInt (String keyName, Integer value, JSONObject jsonObject) {
        jsonObject.put(keyName, value);
        return jsonObject;
    }
    private JSONObject replaceValueInJsonFromFile (String pathToJsonWithValue, String keyName, JSONObject jsonObject) {
        JSONObject jsonWithNewValue = takeJsonToSend(pathToJsonWithValue);
        jsonObject.put(keyName, jsonWithNewValue.get(keyName));
        return jsonObject;
    }

    public void postRequestChooseTypeAndSendAndChangeJsonAndCheckStatus(String url, String keyName, String value, String pathToJsonWithValue, int code, String valueCase, Map<String, ?> header, Map<String, ?> params, JSONObject jsonObject) {
        switch (valueCase) {
            case ("INT"):
                sendPOSTRequestSendJsonCheckStatusCode(url, code, header, params, replaceValueInJsonToInt(keyName, Integer.valueOf(value), jsonObject));
                break;
            case ("STRING"):
                sendPOSTRequestSendJsonCheckStatusCode(url, code, header, params, replaceValueInJsonToString(keyName, value, jsonObject));
                break;
            case ("FILE"):
                sendPOSTRequestSendJsonCheckStatusCode(url, code, header, params, replaceValueInJsonFromFile(pathToJsonWithValue, keyName, jsonObject));
                break;

        }
    }
}

