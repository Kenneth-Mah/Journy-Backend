package sg.edu.nus.journybackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.dto.CustomerDto;
import sg.edu.nus.journybackend.entity.Customer;
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
}
