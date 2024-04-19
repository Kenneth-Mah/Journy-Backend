package sg.edu.nus.journybackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.auth.AuthenticationRequest;
import sg.edu.nus.journybackend.auth.AuthenticationResponse;
import sg.edu.nus.journybackend.auth.RegisterRequest;
import sg.edu.nus.journybackend.config.JwtService;
import sg.edu.nus.journybackend.entity.Member;
import sg.edu.nus.journybackend.enums.Role;
import sg.edu.nus.journybackend.exception.InvalidFollowException;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.repository.MemberRepository;
import sg.edu.nus.journybackend.service.MemberService;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        boolean memberExists = memberRepository.findByUsername(request.getUsername()).isPresent() ||
                memberRepository.findByEmail(request.getEmail()).isPresent();

        if (!memberExists) {
            Member newMember = Member.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .email(request.getEmail())
                    .role(Role.USER)
                    .build();
            memberRepository.save(newMember);
            String jwtToken = jwtService.generateToken(newMember);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new IllegalArgumentException("A member with username or email already exists.");
        }
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Member member = retrieveMemberByUsername(request.getUsername());
        String jwtToken = jwtService.generateToken(member);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public Member retrieveMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Member with username " + username + " does not exist!"));
    }

    @Override
    public Member retrieveMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with id " + memberId + " does not exist!"));
    }

    @Override
    public void followByMemberId(Long memberId, Long targetMemberId) {
        // Check if the member is trying to follow themselves
        if (memberId.equals(targetMemberId)) {
            throw new InvalidFollowException("Unable to follow/unfollow yourself!");
        }

        // Check if the member is already following the target member
        Boolean isAlreadyFollowing = memberRepository.existsByMemberIdAndFollowingMembers_MemberId(memberId, targetMemberId);
        if (isAlreadyFollowing) {
            throw new InvalidFollowException(String.format("MemberID: %s is already following %s, unable to follow", memberId, targetMemberId));
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with id " + memberId + " does not exist!"));
        Member targetMember = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with id " + targetMemberId + " does not exist!"));


        member.getFollowingMembers().add(targetMember);
        targetMember.getFollowersMembers().add(member);

        memberRepository.save(member);
        memberRepository.save(targetMember);
    }

    @Override
    public void unfollowByMemberId(Long memberId, Long targetMemberId) {
        // Check if the member is trying to unfollow themselves
        if (memberId.equals(targetMemberId)) {
            throw new InvalidFollowException("Unable to follow/unfollow yourself!");
        }

        // Check if the member is not following the target member
        Boolean isAlreadyFollowing = memberRepository.existsByMemberIdAndFollowingMembers_MemberId(memberId, targetMemberId);
        if (!isAlreadyFollowing) {
            throw new InvalidFollowException(String.format("MemberID: %s is not currently following %s, unable to follow", memberId, targetMemberId));
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with id " + memberId + " does not exist!"));
        Member targetMember = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with id " + targetMemberId + " does not exist!"));

        member.getFollowingMembers().remove(targetMember);
        targetMember.getFollowersMembers().remove(member);

        memberRepository.save(member);
        memberRepository.save(targetMember);
    }

    @Override
    public Member updateMember(Long memberId, Member updatedMember) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with id " + memberId + " does not exist!"));

        member.setName(updatedMember.getName());
        member.setProfilePictureURL(updatedMember.getProfilePictureURL());
        member.setAboutMe(updatedMember.getAboutMe());

        memberRepository.save(member);
        return member;
    }

    @Override
    public Integer getLikesReceived(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with id " + memberId + " does not exist!"));
        return memberRepository.countByLikedPostsCreator(member);
    }

}
