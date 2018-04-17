package test.java;

import org.junit.Test;
import test.java.config.IEndPoint;
import test.java.config.TestConfig4;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class ResponseTimeTests extends TestConfig4 {

    @Test
    public void verifyResponseTimeThresholdViaReqSpec() {
        given()
                .pathParam("videoGameId", "5")
                .when()
                .get(IEndPoint.VIDEOGAMEIDPARAM)
                .then()
                .log().ifValidationFails();
    }


}
