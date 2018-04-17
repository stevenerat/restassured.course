package test.java.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;

public class TestConfig2 {

    public static  RequestSpecification videoGame_requestSpec;
    public static ResponseSpecification videoGame_responseSpec;

    @BeforeClass
    public static void setup() {

        // Fiddler proxy capture
        // RestAssured.proxy("localhost", 8888);

        videoGame_requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(8080)
                .setBasePath("/app")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        // RestAssured.requestSpecification = videoGame_requestSpec;

        videoGame_responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
}
