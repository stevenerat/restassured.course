package test.java;

import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.Test;
import test.java.config.IEndPoint;
import test.java.config.TestConfig3;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;


public class FootBallTests extends TestConfig3 {

    @Test
    public void getAllCompetitionsOneSeason() {
        given().
                spec(football_requestSpec).
                queryParam("season", 2016).
                when().
                get("competitions/").
                then().
                log().all();
    }

    @Test
    public void getTeamCountForOneComp() {
        given().
                spec(football_requestSpec).
        when().
                get("competitions/426/teams").
                then().
                body("count", equalTo(20)).
                log().ifValidationFails();
    }

    @Test
    public void getFirstTeamName() {
        given().
                spec(football_requestSpec).
                when().
                get("competitions/426/teams").
                then().
                body("teams.name[0]", equalTo("Hull City FC")).  // RestAssured automatically converts resp to array
                log().ifValidationFails();
    }

    @Test
    public void getAllTeamData() {
        Response response =
                given().
                        spec(football_requestSpec).
                        when().
                        get("/competitions/426/teams").
                        then().contentType(ContentType.JSON).   // asserts content type is json
                        extract().response();

        String jsonResponseString = response.asString();
        System.out.println(jsonResponseString);

    }

    @Test
    public void extractHeaders() {
        Response response =
                given().
                        spec(football_requestSpec).
                        when().
                        get("/competitions/426/teams").
                        then().contentType(ContentType.JSON).   // asserts content type is json
                        extract().response();

        Headers headers = response.getHeaders();

        String contentType = response.getHeader("Content-Type"); // "application/json;charset=UTF-8" <-- Can verify UTF-8
        System.out.println(contentType);

    }

    @Test
    public void extractTeamName() {
        String firstTeamName  =
                given().
                        spec(football_requestSpec).
                        when().
                        get("/competitions/426/teams").
                        jsonPath().getString("teams.name[0]");

        System.out.println(firstTeamName);


    }

    @Test
    public void extractAllTeamNames() {
        Response response =
                given().
                        spec(football_requestSpec).
                        when().
                        get("/competitions/426/teams").
                        then().contentType(ContentType.JSON).   // asserts content type is json
                        extract().response();

        List<String> teamNames = response.path("teams.name");

        for(String teamName: teamNames) {
            System.out.println(teamName);
        }
    }
}
