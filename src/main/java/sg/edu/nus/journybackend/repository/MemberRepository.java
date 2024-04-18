package sg.edu.nus.journybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.journybackend.entity.Member;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
    Integer countByLikedPostsPostId(Long postId);
    Integer countByLikedPostsCreator(Member creator);
}
