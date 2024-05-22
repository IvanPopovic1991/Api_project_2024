package tests;

import Config.Config;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Constants;
import static io.restassured.RestAssured.given;
public class UsersTests extends Config {

    @Test
    public void getAllUsers() {
        Response response = given()
                .when().get(Constants.getAllUsers);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getUserByIdTest() {
        Response response = given()
                .pathParam("id", "60d0fe4f5311236168a109de")
                .when().get(Constants.getUserById);

        Assert.assertEquals(response.getStatusCode(), 200);
        String firstName = response.jsonPath().get("firstName");
        Assert.assertEquals(firstName, "Bessie");
        response.getBody().print();
    }

    @Test
    public void deleteUserById() {

        String id = "60d0fe4f5311236168a109d0";
        Response response = given()
                .pathParam("id", id)
                .when().delete(Constants.deleteUSerById);

        Assert.assertEquals(response.getStatusCode(), 200);
        String userId = response.jsonPath().get("id");
        System.out.println(userId);

        Assert.assertEquals(userId, id);

        Response errorResponse = given()
                .pathParam("id", id)
                .when().delete(Constants.deleteUSerById);

        Assert.assertEquals(errorResponse.getStatusCode(), 404);
    }

    @Test
    public void createUser() {
        Response response = given()
                .body("{\n" +
                        "    \"firstName\" : \"Testq\",\n" +
                        "    \"lastName\" : \"Testa\", \n" +
                        "    \"email\":\"testqtesta29121991@mailinator.com.com\"\n" +
                        "}")
                .when().post(Constants.createUser);

        Assert.assertEquals(response.getStatusCode(), 200);
        response.body().print();
    }

    @Test
    public void updateUser() {
        String id = "60d0fe4f5311236168a109cc";
        Response response = given()
                .pathParam("id", id)
                .body("{\n" +
                        "    \"firstName\" : \"Updated firstName \",\n" +
                        "    \"lastName\": \"Updated lastName \"\n" +
                        "}")
                .when().put(Constants.updateUser);

        Assert.assertEquals(response.getStatusCode(), 200);
        response.body().print();
    }
}
