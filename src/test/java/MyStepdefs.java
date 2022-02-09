import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.json.simple.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class MyStepdefs {
    Map<String, String> header = new HashMap<>();
    Map<String, Object> params = new HashMap<>();
    Map<String, String> body = new HashMap<>();
    private String nameOfJson = "";
    @Before(order = 1)
    public void setup() {
/*        RestAssured.baseURI = "https://api.todoist.com/rest/v1/";*/

    }
    BaseAPI bapi = new BaseAPI();
    private void prepareData(List<List<String>> table) {
        for (List<String> strings : table) {
            switch (strings.get(0)) {
                case ("HEADER"):
                    header.put(strings.get(1), strings.get(2));
                            break;
                case ("PARAMS"):
                    params.put(strings.get(1), strings.get(2));
                    break;
                case ("BODY"):
                    body.put(strings.get(1), strings.get(2));
                    nameOfJson = strings.get(2);
                    break;

            }
        }
    }

    @Given("GET request on {string} link with header and status code {int}")
    public void getRequestOnLinkWithHeaderAndStatusCode(String url, int code, DataTable arg) {
            List<List<String>> table = arg.asLists(String.class);
            prepareData(table);
            bapi.getRequestCheckStatusCode(url, code, header, params);

    }

    @Given("POST request on {string} link with authorization header, project name and status code {int}")
    public void postRequestOnLinkWithAuthorizationHeaderProjectNameAndStatusCode(String url, int code, DataTable arg) {
        List<List<String>> table = arg.asLists(String.class);
        prepareData(table);
        bapi.sendPOSTRequestSendJsonCheckStatusCode(url, code, header, params, bapi.takeJsonToSend(nameOfJson));
    }

    @Given("GET request on {string} link with data from {string} and save in variable {} and check status code {int}")
    public void getRequestOnLinkWithDataFromAndSaveInVariableNamesAndCheckStatusCode(String url, String valueFrom, String var, int code, DataTable arg) {
        List<List<String>> table = arg.asLists(String.class);
        prepareData(table);
        bapi.jsonPathGetRequestCheckStatusCode(url, valueFrom, var, code, header, params);
    }

    @Given("GET request on {string} link and check JSON file {} and check status code {int}")
    public void getRequestOnLinkAndCheckJSONFileJsonsToCheckGetProjectAndCheckStatusCode(String url, String jsonFileName, int code, DataTable arg) {
        List<List<String>> table = arg.asLists(String.class);
        prepareData(table);
        bapi.getJsonStructureCheckStatusCodeCheck(url, code, header, params, jsonFileName);

    }

    @Given("GET request on {string} link, rename key {} to the new name {} and check JSON file {} and check status code {int}")
    public void getRequestOnLinkRenameKeyNameToNewNameThisIsNotNameAndCheckJSONFileJsonsToCheckGetProjectAndCheckStatusCode(String url, String keyToDelete, String newKeyName, String jsonFileName, int code, DataTable arg) {
        List<List<String>> table = arg.asLists(String.class);
        prepareData(table);
        bapi.getJsonStructureWithJsonKeyChangeAndCheckStatusCode(url, keyToDelete, newKeyName, jsonFileName, code, header, params);
    }
}
