package sg.edu.nus.journybackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.dto.CustomerDto;
import sg.edu.nus.journybackend.entity.Customer;
import sg.edu.nus.journybackend.exception.CustomerNotFoundException;
import sg.edu.nus.journybackend.exception.InvalidLoginCredentialException;
import sg.edu.nus.journybackend.mapper.CustomerMapper;
import sg.edu.nus.journybackend.repository.CustomerRepository;
import sg.edu.nus.journybackend.service.CustomerService;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.mapToCustomerDto(savedCustomer);
    }

    @Override
    public CustomerDto registerCustomer(CustomerDto customerDto) {
        boolean customerExists = customerRepository.findByUsername(customerDto.getUsername()).isPresent() ||
                customerRepository.findByEmail(customerDto.getEmail()).isPresent();

        if (!customerExists) {
            return createCustomer(customerDto);
        } else {
            throw new IllegalArgumentException("A customer with username or email already exists.");
        }
    }

    @Override
    public CustomerDto login(String username, String password) {
        boolean customerExist = customerRepository.findByUsername(username).isPresent();

        if (!customerExist) {
            throw new CustomerNotFoundException("Customer with username " + username + " does not exist!");
        } else {
            Customer customer = customerRepository.findByUsername(username).get();

            if (customer.getPassword().equals(password)) {
                return CustomerMapper.mapToCustomerDto(customer);
            } else {
                throw new InvalidLoginCredentialException("Invalid username or password!");
            }
        }
    }

}
