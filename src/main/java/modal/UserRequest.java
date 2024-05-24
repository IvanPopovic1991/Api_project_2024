package modal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javafaker.Faker;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class UserRequest {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    private String gender;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("location")
    private UserLocation location;

    public static UserRequest createUser() {

        Faker faker = new Faker();

        UserLocation userLocation = UserLocation.builder()
                .street(faker.address().streetAddress())
                .city(faker.address().city())
                .state(faker.address().state())
                .country(faker.address().country())
                .build();

       //  UserRequest user = UserRequest.builder()
        return UserRequest.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().phoneNumber())
                .location(userLocation)
                .build();
    }
}
