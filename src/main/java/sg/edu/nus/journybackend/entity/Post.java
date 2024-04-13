package sg.edu.nus.journybackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDateTime;
    private Integer likeCount;

    @OneToOne(mappedBy = "post")
    private KMLFile kmlFile;

    @ManyToOne
    @JsonIgnore
    private Member creator;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
