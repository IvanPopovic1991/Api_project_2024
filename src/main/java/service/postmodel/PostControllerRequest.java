package service.postmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class PostControllerRequest {
    @JsonProperty("text")
    private String text;
    @JsonProperty("image")
    private String image;
    @JsonProperty("likes")
    private int likes;
    @JsonProperty("tags")
    private String[] tags;
    @JsonProperty("link")
    private String link;
    @JsonProperty("owner")
    private String ownerId;
    }
