package sg.edu.nus.journybackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sg.edu.nus.journybackend.enums.Role;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member implements UserDetails {
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

    private String profilePictureURL;
    private String aboutMe;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Transient
    private Integer likesReceived;

    @OneToMany
    private List<Post> likedPosts;

    @Transient
    private Integer followingCount;
    @Transient
    private Integer followersCount;

    @OneToMany
    @JsonIgnore
    private List<Member> followingMembers;
    @OneToMany
    @JsonIgnore
    private List<Member> followersMembers;

    @OneToMany(mappedBy = "creator")
    private List<Post> posts;

    @OneToMany(mappedBy = "commenter")
    private List<Comment> comments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
