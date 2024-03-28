package sg.edu.nus.journybackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.entity.Member;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.exception.InvalidLoginCredentialException;
import sg.edu.nus.journybackend.repository.MemberRepository;
import sg.edu.nus.journybackend.service.MemberService;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Override
    public Member registerCustomer(Member newMember) {
        boolean customerExists = memberRepository.findByUsername(newMember.getUsername()).isPresent() ||
                memberRepository.findByEmail(newMember.getEmail()).isPresent();

        if (!customerExists) {
            return memberRepository.save(newMember);
        } else {
            throw new IllegalArgumentException("A member with username or email already exists.");
        }
    }

    @Override
    public Member login(String username, String password) {
        boolean memberExist = memberRepository.findByUsername(username).isPresent();

        if (!memberExist) {
            throw new ResourceNotFoundException("Member with username " + username + " does not exist!");
        } else {
            Member member = memberRepository.findByUsername(username).get();

            if (member.getPassword().equals(password)) {
                return member;
            } else {
                throw new InvalidLoginCredentialException("Invalid username or password!");
            }
        }
    }

}
