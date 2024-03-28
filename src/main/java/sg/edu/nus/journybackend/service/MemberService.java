package sg.edu.nus.journybackend.service;

import sg.edu.nus.journybackend.entity.Member;

public interface MemberService {
    Member registerCustomer(Member newMember);

    Member login(String username, String password);
}
