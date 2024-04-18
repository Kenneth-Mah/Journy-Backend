package sg.edu.nus.journybackend.service;

import sg.edu.nus.journybackend.auth.AuthenticationRequest;
import sg.edu.nus.journybackend.auth.AuthenticationResponse;
import sg.edu.nus.journybackend.auth.RegisterRequest;
import sg.edu.nus.journybackend.entity.Member;

public interface MemberService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    Member retrieveMemberByUsername(String username);

    Member retrieveMemberById(Long memberId);

    void followByMemberId(Long memberId, Long targetMemberId);

    Member updateMember(Long memberId, Member updatedMember);

    Integer getLikesReceived(Long memberId);
}
