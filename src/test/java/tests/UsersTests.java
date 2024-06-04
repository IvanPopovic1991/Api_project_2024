package tests;

import Config.Config;
import io.restassured.response.Response;
import listeners.RetryAnalyzer;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import user.UserService;
import usermodel.UserRequest;
import usermodel.UserResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.Constants;
import utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static utils.Constants.*;
import static utils.Utils.createJsonFile;

@Listeners(TestListener.class)
public class UsersTests extends Config {
    SoftAssert softAssert;
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        softAssert = new SoftAssert();
    }
    @Test(description = "Get all users from DB; Expected results: All users are retrieved", groups = "smoke,regresion",
    retryAnalyzer = RetryAnalyzer.class)
    public void getAllUsers() {
        UserRequest userRequest = UserService.createUserTemplate();

        Map<String, String> map = new HashMap<>();
        map.put("page","2");
        map.put("limit","50");

        UserResponse userResponse = given()
                .body(userRequest)
                .when().post(createUser).getBody().as(UserResponse.class);

        List<UserResponse> response = given()
                .queryParams(map)
                .when().get(getAllUsers).jsonPath().getList("data", UserResponse.class);

        String expectedId = userResponse.getId();

        boolean isInTheList = false;
        for (int i = 0; i < response.size() ; i++) {
            if (response.get(i).getId().equals(expectedId)){
                isInTheList = true;
            }
        }
        Assert.assertTrue(isInTheList);

//        Assert.assertEquals(response.getStatusCode(), 200);
//        String actualFirstName = response.jsonPath(("data[0].firstName");
//        System.out.println();
    }
    @Test
    public void getUserByIdTest() {

        UserRequest newUser = UserService.createUserTemplate();

        UserResponse userResponse = given()
                .body(newUser)
                .when().post(createUser).getBody().as(UserResponse.class);

        String newUserId = userResponse.getId();
        UserResponse newUserResponse = given()
                .pathParam("id", newUserId)
                .when().get(Constants.getUserById).getBody().as(UserResponse.class);

        softAssert.assertEquals(newUserResponse.getFirstName(), newUser.getFirstName());
        softAssert.assertEquals(newUserResponse.getLastName(), newUser.getLastName());
        softAssert.assertEquals(newUserResponse.getId(), newUserId);
        softAssert.assertAll();
    }
    @Test
    public void deleteUserById() {

        UserRequest newUser = UserService.createUserTemplate();

        UserResponse userResponse = given()
                .body(newUser)
                .when().post(createUser).getBody().as(UserResponse.class);

        String newUserId = userResponse.getId();

        UserResponse newUserResponse = given()
                .pathParam("id", newUserId)
                .when().delete(deleteUSerById).getBody().as(UserResponse.class);

        softAssert.assertEquals(newUserResponse.getId(), newUserId);
        softAssert.assertAll();
    }

//    @Test(enabled = false)
//    public void createUser() {
//        Response response = given()
//                .body("{\n" +
//                        "    \"firstName\" : \"Testq\",\n" +
//                        "    \"lastName\" : \"Testa\", \n" +
//                        "    \"email\":\"testqtesta29121991@mailinator.com.com\"\n" +
//                        "}")
//                .when().post(createUser);
//
//        Assert.assertEquals(response.getStatusCode(), 200);
//        response.body().print();
//    }
//
//    @Test(dependsOnMethods = "createUser")
//    public void updateUser() {
//        String id = "60d0fe4f5311236168a109cc";
//        Response response = given()
//                .pathParam("id", id)
//                .body("{\n" +
//                        "    \"firstName\" : \"Updated firstName \",\n" +
//                        "    \"lastName\": \"Updated lastName \"\n" +
//                        "}")
//                .when().put(Constants.updateUser);
//
//        Assert.assertEquals(response.getStatusCode(), 200);
//        response.body().print();
//    }
    @Test
    public void createUserUsingJavaObjectTest() {

        UserRequest userRequest = UserService.createUserTemplate();
        createJsonFile("userRequest",userRequest);

        UserResponse userResponse = given()
                .body(userRequest)
                .when().post(createUser).getBody().as(UserResponse.class);

        softAssert.assertEquals(userResponse.getEmail(), userRequest.getEmail());
        softAssert.assertEquals(userResponse.getFirstName(), userRequest.getFirstName());
        softAssert.assertEquals(userResponse.getLastName(), userRequest.getLastName());
        softAssert.assertEquals(userResponse.getLocation(), userRequest.getLocation());
        softAssert.assertAll();
    }
    @Test
    public void updateUserUsingJavaObjectTest() {

        UserRequest user = UserService.createUserTemplate();

        UserResponse userResponse = given()
                .body(user)
                .when().post(createUser).getBody().as(UserResponse.class);

        String updatedFirstName = "FirstNameUpdated";
        String updatedEmail = "EmailUpdated";
        String updatedCity = "CityUpdated";

//        userRequest.setFirstName(updatedFirstName);
//        userRequest.setEmail(updatedEmail);
//        userRequest.getLocation().setCity(updatedCity);

        UserRequest updatedUser = user
                .withFirstName(updatedFirstName)
                .withEmail(updatedEmail)
                .withLocation(user.getLocation().withCity(updatedCity));

        String id = userResponse.getId();
        UserResponse updatedUserResponse = given()
                .body(updatedUser)
                .pathParam("id", id)
                .when().put(updateUser).getBody().as(UserResponse.class);

//        softAssert.assertEquals(updatedUserResponse.getEmail(), updatedEmail);
        softAssert.assertEquals(updatedUserResponse.getFirstName(), updatedFirstName);
        softAssert.assertEquals(updatedUserResponse.getLastName(), user.getLastName());
        softAssert.assertEquals(updatedUserResponse.getLocation().getCity(), updatedCity);
        softAssert.assertAll();
    }
    @Test
    public void readFromJson(){
        UserResponse userResponse = Utils.getUserFromJson("userRequest");
        System.out.println(userResponse);
    }
}
