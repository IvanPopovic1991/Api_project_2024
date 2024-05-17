package tests;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Constants;
import static io.restassured.RestAssured.given;

public class UsersTests {

    @Test
    public void getUserByIdTest() {
        Response response = given()
                .baseUri("https://dummyapi.io/data")
                .basePath("/v1")
                .log().all()
                .header("app-id", "663e187ed6c4841db0c6183f")
                .log().all()
                .pathParam("id", "60d0fe4f5311236168a109ca")
                .when().get(Constants.getUserById);

        Assert.assertEquals(response.getStatusCode(), 200);
        response.getBody().print();
    }
}
