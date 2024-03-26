package sg.edu.nus.journybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sg.edu.nus.journybackend.entity.Comment;
import sg.edu.nus.journybackend.entity.Post;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String id;
    private String username;
    private String password;
    private String name;
    private String email;

    private List<Comment> comments;
    private List<Post> posts;
}
