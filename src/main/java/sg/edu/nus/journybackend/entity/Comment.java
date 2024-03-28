package sg.edu.nus.journybackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("comment")
public class Comment {
    @Id
    private String commentId;
    private Date commentDateTime;
    private String commentDetails;

    @DBRef
    private Customer commenter;

    @DBRef
    private Post post;
}
