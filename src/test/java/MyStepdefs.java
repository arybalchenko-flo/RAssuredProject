import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class MyStepdefs {
    Map<String, String> header = new HashMap<>();
    Map<String, Object> params = new HashMap<>();
    @Before(order = 1)
    public void setup() {
        RestAssured.baseURI = "https://api.todoist.com/rest/v1/";
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
            }
        }
    }

    @Given("GET request on {string} link with header and status code {int}")
    public void getRequestOnLinkWithHeaderAndStatusCode(String url, int code, DataTable arg) {
            List<List<String>> table = arg.asLists(String.class);
            System.out.println(table);
            prepareData(table);
            bapi.getRequestCheckStatusCode(url, code, header, params);
    }
}
