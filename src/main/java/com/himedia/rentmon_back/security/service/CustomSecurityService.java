//package com.himedia.rentmon_back.security.service;
//
//import com.himedia.rentmon_back.dto.MemberDTO;
//import com.himedia.rentmon_back.entity.Member;
//import com.himedia.rentmon_back.repository.MemberRepository;
//import com.himedia.rentmon_back.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Log4j2
//@RequiredArgsConstructor
//public class CustomSecurityService implements UserDetailsService {
//    private final MemberRepository mr;
//
//    @Override
//    public UserDetails loadUserByUsername(String usernameWithRole) throws UsernameNotFoundException {
//        String[] parts = usernameWithRole.split(":");
//
//        // Debug print for checking
//        System.out.println("usernameWithRole: " + usernameWithRole);
//        if (parts.length != 2) {
//            throw new UsernameNotFoundException("username or role incorrect");
//        }
//
//        String username = parts[0];
//        String role = parts[1];
//
//        Member member = (Member) mr.findByUseridAndRole(username, role)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        MemberDTO mDTO = new MemberDTO(member.getUserid(), member.getPwd(), member.getCreated_at(), member.getRole());
//
//        log.info("Member found: " + member);
//        log.info("MemberDTO: " + mDTO);
//
//        return mDTO;
//    }
//}
