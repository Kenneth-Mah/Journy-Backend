//package sg.edu.nus.journybackend.controller;
//
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import sg.edu.nus.journybackend.dto.MemberDto;
//import sg.edu.nus.journybackend.dto.LoginDto;
//import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
//import sg.edu.nus.journybackend.exception.InvalidLoginCredentialException;
//import sg.edu.nus.journybackend.service.CustomerService;
//
//@CrossOrigin("*")
//@AllArgsConstructor
//@RestController
//@RequestMapping("/api/customers")
//public class CustomerController {
//
//    private CustomerService customerService;
//
//    @PostMapping
//    public ResponseEntity<MemberDto> createCustomer(@RequestBody MemberDto memberDto) {
//        MemberDto savedMemberDto = customerService.createCustomer(memberDto);
//        return new ResponseEntity<>(savedMemberDto, HttpStatus.CREATED);
//    }
//
//    //Can access through the endpoint /api/customers/register
//    @PostMapping("/register")
//    public ResponseEntity<?> registerCustomer(@RequestBody MemberDto memberDto) {
//        try{
//            MemberDto newMemberDto = customerService.registerCustomer(memberDto);
//
//            return new ResponseEntity<>(newMemberDto, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
//        try{
//            MemberDto loginCustomer = customerService.login(loginDto.getUsername(), loginDto.getPassword());
//
//            return new ResponseEntity<>(loginCustomer, HttpStatus.OK);
//        } catch (InvalidLoginCredentialException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//}
