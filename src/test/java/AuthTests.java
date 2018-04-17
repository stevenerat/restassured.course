package test.java;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

public class AuthTests {

    @BeforeClass
    public static void setup() {

        // proxy to fiddler
        RestAssured.proxy("localhost", 8888);
    }
}
