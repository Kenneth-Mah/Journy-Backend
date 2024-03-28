//package sg.edu.nus.journybackend.service.impl;
//
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import sg.edu.nus.journybackend.dto.MemberDto;
//import sg.edu.nus.journybackend.entity.Member;
//import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
//import sg.edu.nus.journybackend.exception.InvalidLoginCredentialException;
//import sg.edu.nus.journybackend.mapper.CustomerMapper;
//import sg.edu.nus.journybackend.repository.CustomerRepository;
//import sg.edu.nus.journybackend.service.CustomerService;
//
//import java.util.ArrayList;
//
//@Service
//@AllArgsConstructor
//public class CustomerServiceImpl implements CustomerService {
//
//    private CustomerRepository customerRepository;
//
//    @Override
//    public MemberDto createCustomer(MemberDto memberDto) {
//        memberDto.setComments(new ArrayList<>());
//        memberDto.setPosts(new ArrayList<>());
//
//        Member member = CustomerMapper.mapToCustomer(memberDto);
//        Member savedMember = customerRepository.save(member);
//        return CustomerMapper.mapToCustomerDto(savedMember);
//    }
//
//    @Override
//    public MemberDto registerCustomer(MemberDto memberDto) {
//        boolean customerExists = customerRepository.findByUsername(memberDto.getUsername()).isPresent() ||
//                customerRepository.findByEmail(memberDto.getEmail()).isPresent();
//
//        if (!customerExists) {
//            return createCustomer(memberDto);
//        } else {
//            throw new IllegalArgumentException("A customer with username or email already exists.");
//        }
//    }
//
//    @Override
//    public MemberDto login(String username, String password) {
//        boolean customerExist = customerRepository.findByUsername(username).isPresent();
//
//        if (!customerExist) {
//            throw new ResourceNotFoundException("Customer with username " + username + " does not exist!");
//        } else {
//            Member member = customerRepository.findByUsername(username).get();
//
//            if (member.getPassword().equals(password)) {
//                return CustomerMapper.mapToCustomerDto(member);
//            } else {
//                throw new InvalidLoginCredentialException("Invalid username or password!");
//            }
//        }
//    }
//
//}
