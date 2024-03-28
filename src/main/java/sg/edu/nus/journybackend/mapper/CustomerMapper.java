//package sg.edu.nus.journybackend.mapper;
//
//import sg.edu.nus.journybackend.dto.MemberDto;
//import sg.edu.nus.journybackend.entity.Member;
//
//public class CustomerMapper {
//    public static MemberDto mapToCustomerDto(Member member) {
//        return new MemberDto(
//                member.getMemberId(),
//                member.getUsername(),
//                member.getPassword(),
//                member.getName(),
//                member.getEmail(),
//                member.getComments(),
//                member.getPosts()
//        );
//    }
//
//    public static Member mapToCustomer(MemberDto memberDto) {
//        return new Member(
//                memberDto.getMemberId(),
//                memberDto.getUsername(),
//                memberDto.getPassword(),
//                memberDto.getName(),
//                memberDto.getEmail(),
//                memberDto.getComments(),
//                memberDto.getPosts()
//        );
//    }
//}
