package sg.edu.nus.journybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.journybackend.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
