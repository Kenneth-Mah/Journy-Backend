package sg.edu.nus.journybackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("post")
public class Post {
    @Id
    private String postId;
    private Date createdDateTime;
    private Integer likeCount;

    @DBRef
    private KMLFile kmlFile;

    @DBRef
    private Customer creator;

    @JsonIgnore
    private List<Comment> commentList;
}
