package sg.edu.nus.journybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sg.edu.nus.journybackend.entity.Comment;
import sg.edu.nus.journybackend.entity.Customer;
import sg.edu.nus.journybackend.entity.KMLFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private String postId;
    private Date createdDateTime;
    private Integer likeCount;
    private KMLFile kmlFile;
    private Customer creator;
    private List<Comment> commentList = new ArrayList<>();
}
