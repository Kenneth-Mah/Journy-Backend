package sg.edu.nus.journybackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sg.edu.nus.journybackend.entity.Post;

public interface PostRepository extends MongoRepository<Post, String> {

}
