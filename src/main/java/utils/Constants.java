package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String getAllUsers = "user";
    public static final String getUserById = "user/{id}";
    public static final String deleteUSerById = "user/{id}";
    public static final String createUser= "user/create";
    public static final String updateUser = "user/{id}";
    public static final String projectRoot = System.getProperty("user.dir");
    public static final String createPost = "post/create";
    public static final String getPostList = "post";
    public static final String getPostsByUser = "user/{id}/post";
    public static final String getPostById = "post/{id}";
    public static final String getPostByTag = "tag/{id}/post";
    public static final String updatePost = "post/{id}";
    public static final String deletePost = "post/{id}";

}
