package test.java;

import org.junit.Test;
import test.java.config.TestConfig1;

import static io.restassured.RestAssured.*;

public class MyTest1 extends TestConfig1 {

    @Test
    public void myFirstTest() {
        given()
                .log().all()
                .when().get("videogames/1")
                .then()
                .log().all();
                //.statusCode(200);
    }
}
