package test.java.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;

import static org.hamcrest.Matchers.lessThan;

public class GPathTestConfig {

    public static  RequestSpecification videoGame_requestSpec;
    public static RequestSpecification  football_requestSpec;
    public static ResponseSpecification responseSpec;

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
        
        football_requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://api.football-data.org")
                .setBasePath("/v1")
                //.addHeader("X-AuthToken", "xxxxxx")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("X-Response-Control", "minified")
                .build();

        RestAssured.requestSpecification = football_requestSpec;

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectResponseTime(lessThan(1000L))  // Fore every response verify status code AND response time
                .build();

        RestAssured.responseSpecification = responseSpec;
    }
}
