package sg.edu.nus.journybackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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
    @JsonIgnore
    private Member commenter;

    @ManyToOne
    @JsonIgnore
    private Post post;
}
