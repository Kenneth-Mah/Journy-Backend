package sg.edu.nus.journybackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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

    private String postPicture;
    private String postTitle;
    private String postDescription;
    private double budget;

    @ElementCollection
    @CollectionTable(name = "post_locations", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "location")
    private List<String> locations = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private Member creator;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
