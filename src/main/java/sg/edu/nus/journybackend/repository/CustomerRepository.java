package sg.edu.nus.journybackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import sg.edu.nus.journybackend.entity.Customer;
import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByEmail(String email);
}
