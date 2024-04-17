package sg.edu.nus.journybackend.service;

import sg.edu.nus.journybackend.auth.AuthenticationRequest;
import sg.edu.nus.journybackend.auth.AuthenticationResponse;
import sg.edu.nus.journybackend.auth.RegisterRequest;
import sg.edu.nus.journybackend.entity.Member;

public interface MemberService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    Member findByUsername(String username);

    void followByMemberId(Long memberId, Long targetMemberId);
}
