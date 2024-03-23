package sg.edu.nus.journybackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sg.edu.nus.journybackend.entity.KMLFile;

public interface KMLFileRepository extends MongoRepository<KMLFile, String> {
}
