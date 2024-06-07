package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import usermodel.UserResponse;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import static utils.Constants.projectRoot;

public class Utils {

    public static void createJsonFile(String fileName, Object o) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(projectRoot + "/src/test/resources/" + fileName + ".json"), o);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object readFromJson(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(Paths.get(path).toFile(), Object.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserResponse getUserFromJson(String fileName) {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(projectRoot + "/src/test/resources/" + fileName + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Gson().fromJson(reader, new TypeToken<UserResponse>() {
        }.getType());
    }
}
