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

}
