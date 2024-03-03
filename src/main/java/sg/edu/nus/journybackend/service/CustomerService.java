package sg.edu.nus.journybackend.service;

import sg.edu.nus.journybackend.dto.CustomerDto;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    CustomerDto registerCustomer(CustomerDto customerDto);

    CustomerDto login(String username, String password);
}
