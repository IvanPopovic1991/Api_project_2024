package service.postmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class PostControllerResponse {
    @JsonProperty("data")
    private List data;
    @JsonProperty("total")
    private int total;
    @JsonProperty("page")
    private int page;
    @JsonProperty("limit")
    private int limit;
    @JsonProperty("id")
    private String id;
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
    private String ownerId;
    @JsonProperty("owner")
    private Owner owner;
    @JsonFormat(shape=JsonFormat.Shape.STRING ,pattern="yyyy-MM-dd")
    private Date publishDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date updatedDate;
}
