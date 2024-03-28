package sg.edu.nus.journybackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    private String name;
    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "commenter")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "creator")
    private List<Post> posts = new ArrayList<>();
}
