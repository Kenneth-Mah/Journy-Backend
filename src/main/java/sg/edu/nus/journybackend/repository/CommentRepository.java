package sg.edu.nus.journybackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sg.edu.nus.journybackend.entity.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
