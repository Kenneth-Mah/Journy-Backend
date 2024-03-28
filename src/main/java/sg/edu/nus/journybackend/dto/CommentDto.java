package sg.edu.nus.journybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sg.edu.nus.journybackend.entity.Customer;
import sg.edu.nus.journybackend.entity.Post;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String commentId;
    private Date commentDateTime;
    private String commentDetails;
    private Customer commenter;
    private Post post;
}
