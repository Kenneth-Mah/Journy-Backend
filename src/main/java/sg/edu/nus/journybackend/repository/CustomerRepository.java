package sg.edu.nus.journybackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sg.edu.nus.journybackend.entity.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
