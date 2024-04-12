package sg.edu.nus.journybackend.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.entity.Member;
import sg.edu.nus.journybackend.entity.Comment;
import sg.edu.nus.journybackend.entity.Post;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.exception.InvalidCredentialException;
import sg.edu.nus.journybackend.repository.MemberRepository;
import sg.edu.nus.journybackend.service.MemberService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    @PersistenceContext
    private EntityManager em;

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
                throw new InvalidCredentialException("Invalid username or password!");
            }
        }
    }

}
