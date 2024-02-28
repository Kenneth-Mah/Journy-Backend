package sg.edu.nus.journybackend.mapper;

import sg.edu.nus.journybackend.dto.CustomerDto;
import sg.edu.nus.journybackend.entity.Customer;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getUsername(),
                customer.getPassword(),
                customer.getName()
        );
    }

    public static Customer mapToCustomer(CustomerDto customerDto) {
        return new Customer(
                customerDto.getId(),
                customerDto.getUsername(),
                customerDto.getPassword(),
                customerDto.getName()
        );
    }
}
