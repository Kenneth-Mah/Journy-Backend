package sg.edu.nus.journybackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.journybackend.dto.CustomerDto;
import sg.edu.nus.journybackend.dto.LoginDto;
import sg.edu.nus.journybackend.exception.CustomerNotFoundException;
import sg.edu.nus.journybackend.exception.InvalidLoginCredentialException;
import sg.edu.nus.journybackend.service.CustomerService;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto savedCustomerDto = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(savedCustomerDto, HttpStatus.CREATED);
    }

    //Can access through the endpoint /api/customers/register
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerDto customerDto) {
        try{
            CustomerDto newCustomerDto = customerService.registerCustomer(customerDto);

            return new ResponseEntity<>(newCustomerDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try{
            CustomerDto loginCustomer = customerService.login(loginDto.getUsername(), loginDto.getPassword());

            return new ResponseEntity<>(loginCustomer, HttpStatus.OK);
        } catch (InvalidLoginCredentialException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
