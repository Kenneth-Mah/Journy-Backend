package sg.edu.nus.journybackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDateTime;
    private String commentDetails;

    @ManyToOne
    private Member commenter;

    @ManyToOne
    @JsonIgnore
    private Post post;
}
