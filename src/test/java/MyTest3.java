package test.java;

import org.junit.Test;
import test.java.config.IEndPoint;
import test.java.config.TestConfig3;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class MyTest3 extends TestConfig3 {

    @Test
    public void myFirstTest() {
        given()
                .log().all()
                .when().get("videogames/1")
                .then()
                .log().all();

    }

    @Test
    public void getAllGames() {
        when().get(IEndPoint.VIDEOGAMES)
        .then()
        .log().all();
    }
}
