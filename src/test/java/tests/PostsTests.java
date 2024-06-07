package tests;

import Config.Config;
import com.github.javafaker.Faker;
import listeners.TestListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import service.postmodel.PostControllerRequest;
import service.postmodel.PostControllerResponse;
import utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Listeners(TestListener.class)
public class PostsTests extends Config {

    SoftAssert softAssert;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        softAssert = new SoftAssert();
    }

    Faker faker = new Faker(new Locale("en-US"));

    @Test(description = "Create new post; return created post data.")

    public void createPost() {

        PostControllerRequest newPostRequest = PostControllerRequest.builder()
                .text(faker.lorem().sentence())
                .image(faker.internet().image())
                .likes(faker.number().numberBetween(0, 1000))
                .tags(faker.lorem().words().toArray(new String[2]))
                .ownerId("60d0fe4f5311236168a109d5")
                .build();

        PostControllerResponse newPostResponse = given()
                .body(newPostRequest)
                .when().post(Constants.createPost).getBody().as(PostControllerResponse.class);

        softAssert.assertEquals(newPostResponse.getText(), newPostRequest.getText());
        softAssert.assertEquals(newPostResponse.getImage(), newPostRequest.getImage());
        softAssert.assertEquals(newPostResponse.getLikes(), newPostRequest.getLikes());
        softAssert.assertEquals(newPostResponse.getTags(), newPostRequest.getTags());
        softAssert.assertEquals(newPostResponse.getOwner().getId(), newPostRequest.getOwnerId());
        softAssert.assertAll();
    }

    @Test(description = "Get list of posts sorted by creation date.")
    public void getList() {
        PostControllerRequest newPostRequest = PostControllerRequest.builder()
                .text(faker.lorem().sentence())
                .image(faker.internet().image())
                .likes(faker.number().numberBetween(0, 1000))
                .tags(faker.lorem().words().toArray(new String[2]))
                .ownerId("60d0fe4f5311236168a109d5")
                .build();

        PostControllerResponse newPostResponse = given()
                .body(newPostRequest)
                .when().post(Constants.createPost).getBody().as(PostControllerResponse.class);

        Map<String, String> map = new HashMap<>();
        map.put("page", "3");
        map.put("limit", "50");

        String postId = newPostResponse.getId();
        List<PostControllerResponse> postList = given()
                .queryParams(map)
                .when().get(Constants.getPostList).jsonPath().getList("data", PostControllerResponse.class);

        boolean postIsInTheList = false;
        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getOwner().getId().equals(postId)) {
                postIsInTheList = true;
            }
        }

        softAssert.assertTrue(postIsInTheList);
        softAssert.assertAll();
    }

    @Test(description = "Get list of posts for specific user sorted by creation date.")
    public void getListByUser() {
        PostControllerRequest newPostRequest = PostControllerRequest.builder()
                .text(faker.lorem().sentence())
                .image(faker.internet().image())
                .likes(faker.number().numberBetween(0, 1000))
                .tags(faker.lorem().words().toArray(new String[2]))
                .ownerId("60d0fe4f5311236168a109d5")
                .build();

        PostControllerResponse newPostResponse = given()
                .body(newPostRequest)
                .when().post(Constants.createPost).getBody().as(PostControllerResponse.class);

        String ownerID = newPostResponse.getOwner().getId();

        List<PostControllerResponse> postListByUser = given()
                .pathParams("id", ownerID)
                .when().get(Constants.getPostsByUser).jsonPath().getList("data", PostControllerResponse.class);

        boolean userIsInTheList = false;
        for (int i = 0; i < postListByUser.size(); i++) {
            if (postListByUser.get(i).getOwner().getId().equals(ownerID)) {
                userIsInTheList = true;
            }
        }
        softAssert.assertTrue(userIsInTheList);
    }

    @Test(description = "Get post full data by post id.")
    public void getPostById() {
        PostControllerRequest newPostRequest = PostControllerRequest.builder()
                .text(faker.lorem().sentence())
                .image(faker.internet().image())
                .likes(faker.number().numberBetween(0, 1000))
                .tags(faker.lorem().words().toArray(new String[2]))
                .ownerId("60d0fe4f5311236168a109d5")
                .build();

        PostControllerResponse newPostResponse = given()
                .body(newPostRequest)
                .when().post(Constants.createPost).getBody().as(PostControllerResponse.class);

        String postId = newPostResponse.getId();

        PostControllerResponse newPost = given()
                .pathParams("id", postId)
                .when().get(Constants.getPostById).getBody().as(PostControllerResponse.class);

        softAssert.assertEquals(postId, newPost.getId());
        softAssert.assertAll();
    }

    @Test(description = "Get list of posts for specific tag sorted by creation date.")
    public void getPostsByTag() {
        PostControllerRequest newPostRequest = PostControllerRequest.builder()
                .text(faker.lorem().sentence())
                .image(faker.internet().image())
                .likes(faker.number().numberBetween(0, 1000))
                .tags(faker.lorem().words().toArray(new String[2]))
                .ownerId("60d0fe4f5311236168a109d5")
                .build();

        PostControllerResponse newPostResponse = given()
                .body(newPostRequest)
                .when().post(Constants.createPost).getBody().as(PostControllerResponse.class);

        String postId = newPostResponse.getId();
        String expectedTag = newPostResponse.getTags()[1];

        List<PostControllerResponse> postsByTags = given()
                .pathParams("id", postId)
                // .when().get(Constants.getPostByTag).getBody().as(PostControllerResponse.class);
                .when().get(Constants.getPostByTag).jsonPath().getList("data", PostControllerResponse.class);

        boolean actualTag = false;
        for (int i = 0; i < postsByTags.size(); i++) {
            if (postsByTags.get(i).getTags().equals(expectedTag)) {
                actualTag = true;
            }
        }
        softAssert.assertTrue(actualTag);
        softAssert.assertAll();

    }

    @Test(description = "Update post by id, return updated Post data.")
    public void updatePost() {
        PostControllerRequest newPostRequest = PostControllerRequest.builder()
                .text(faker.lorem().sentence())
                .image(faker.internet().image())
                .likes(faker.number().numberBetween(0, 1000))
                .tags(faker.lorem().words().toArray(new String[2]))
                .ownerId("60d0fe4f5311236168a109d5")
                .build();

        PostControllerResponse newPostResponse = given()
                .body(newPostRequest)
                .when().post(Constants.createPost).getBody().as(PostControllerResponse.class);

        String updatedText = "Updated text.";
        String updatedImage = "Updated image.png";
        int updatedLikes = 500;
        String[] updatedTags = {"sunset", "vacation"};

        PostControllerRequest updatedPostRequest = newPostRequest
                .withText(updatedText)
                .withImage(updatedImage)
                .withLikes(updatedLikes)
                .withTags(updatedTags);

        String postId = newPostResponse.getId();

        PostControllerResponse updatedPostResponse = given()
                .body(updatedPostRequest)
                .pathParam("id", postId)
                .when().put(Constants.updatePost).getBody().as(PostControllerResponse.class);

        softAssert.assertEquals(updatedPostResponse.getText(), updatedText);
        softAssert.assertEquals(updatedPostResponse.getImage(), updatedImage);
        softAssert.assertEquals(updatedPostResponse.getLikes(), updatedLikes);
        softAssert.assertEquals(updatedPostResponse.getTags(), updatedTags);
        softAssert.assertAll();
    }

    @Test(description = "Delete post by id, return id of deleted post.")
    public void deletePostById() {
        PostControllerRequest newPostRequest = PostControllerRequest.builder()
                .text(faker.lorem().sentence())
                .image(faker.internet().image())
                .likes(faker.number().numberBetween(0, 1000))
                .tags(faker.lorem().words().toArray(new String[2]))
                .ownerId("60d0fe4f5311236168a109d5")
                .build();

        PostControllerResponse newPostResponse = given()
                .body(newPostRequest)
                .when().post(Constants.createPost).getBody().as(PostControllerResponse.class);

        String postId = newPostResponse.getId();

        PostControllerResponse deletePostById = given()
                .pathParams("id", postId)
                .when().delete(Constants.deletePost).getBody().as(PostControllerResponse.class);

        softAssert.assertEquals(deletePostById.getId(), postId);
        softAssert.assertAll();
    }
}
