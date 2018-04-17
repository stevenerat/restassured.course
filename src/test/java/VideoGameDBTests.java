package test.java;

import main.java.VideoGame;
import org.junit.Test;
import test.java.config.IEndPoint;
import test.java.config.TestConfig3;

import static io.restassured.RestAssured.*;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;

public class VideoGameDBTests extends TestConfig3 {

    @Test
    public void getAllGames() {
        given()
                .when()
                .get(IEndPoint.VIDEOGAMES)
                .then()
                .log().all();
    }

    @Test
    public void createNewGameByJSON() {
        String newGameJSON =
                "{\n" +
                        "  \"id\": 987,\n" +
                        "  \"name\": \"Steve's New Game\",\n" +
                        "  \"releaseDate\": \"2018-04-16T15:09:01.950Z\",\n" +
                        "  \"reviewScore\": 51,\n" +
                        "  \"category\": \"driving\",\n" +
                        "  \"rating\": \"PG\"\n" +
                        "}";
        given()
                .body(newGameJSON)
                .when()
                .post(IEndPoint.VIDEOGAMES)
                .then()
                .log().all();
    }


    @Test
    public void updateGameByJSON() {
        String modifiedGameJSON =
                "{\n" +
                        "  \"id\": 987,\n" +
                        "  \"name\": \"Steve's Updated Game\",\n" +
                        "  \"releaseDate\": \"2018-04-16T15:09:01.950Z\",\n" +
                        "  \"reviewScore\": 51,\n" +
                        "  \"category\": \"driving\",\n" +
                        "  \"rating\": \"PG\"\n" +
                        "}";
        given()
                .body(modifiedGameJSON)
                .when()
                .put(IEndPoint.VIDEOGAMES + "/987")
                .then()
                .log().all();
    }

    @Test
    public void deleteGame() {
        given()
                .when()
                .delete(IEndPoint.VIDEOGAMES + "/987")
                .then()
                .log().all();
    }

    @Test
    public void getGameByID() {
        given()
                .pathParam("videoGameId", "5")
                .when()
                .get(IEndPoint.VIDEOGAMEIDPARAM)
                .then().log().all();
    }

    /**
     * http://pojo.sodhanalibrary.com/Convert
     */
    @Test
    public void videoGameSerializationTest() {

        VideoGame videoGame = new VideoGame("597", "kapow", "2014-06-06", "Antonio 5", "Immature", "89");

        given()
                .body(videoGame) // <-- VideoGame object will be serialized to JSON for the post
                .when()
                .post(IEndPoint.VIDEOGAMES)
                .then()
                .log().all();
    }

    /**
     * https://jsonschema.net
     */
    @Test
    public void validateVideoGameJSONSchema() {
        given().
                pathParam("videoGameId", 5).
                when().
                get(IEndPoint.VIDEOGAMEIDPARAM).
                then().
                body(matchesJsonSchemaInClasspath("VideoGameJSONSchema.json"));

    }

    @Test
    public void printResponseTime() {
        long responseTime =
                given()
                .pathParam("videoGameId", "5")
                .when()
                .get(IEndPoint.VIDEOGAMEIDPARAM)
                .time();

        System.out.println("Response Time: " + responseTime +  " ms");
    }

    @Test
    public void verifyResponseTimeThreshold() {
        given()
                .pathParam("videoGameId", "5")
                .when()
                .get(IEndPoint.VIDEOGAMEIDPARAM)
                .then()
                .time(lessThan(1000L));
    }


}
