package test.java;

import org.junit.Test;
import test.java.config.TestConfig2;

import static io.restassured.RestAssured.given;

public class MyTest2 extends TestConfig2 {

    @Test
    public void myFirstTest() {
        given()
                .spec(videoGame_requestSpec)
                .log().all()
                .when().get("videogames/2")
                .then()
                .spec(videoGame_responseSpec)
                .log().all();
    }
}
