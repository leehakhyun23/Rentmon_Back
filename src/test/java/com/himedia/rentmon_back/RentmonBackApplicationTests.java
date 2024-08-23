package com.himedia.rentmon_back;

import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.Member;
import com.himedia.rentmon_back.repository.HostRepository;
import com.himedia.rentmon_back.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class RentmonBackApplicationTests {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HostRepository hostRepository;

    @Test
    void contextLoads() {
        PasswordEncoder ps = new BCryptPasswordEncoder();
        System.out.println(ps.encode("1234"));
    }

    @Test
    void insertMember() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        List<Member> members = Arrays.asList(
                new Member(0, "user1", passwordEncoder.encode("1234"), "user", "김치만두", null),
                new Member(0, "user2", passwordEncoder.encode("1234"), "user", "동안사장", null),
                new Member(0, "user3", passwordEncoder.encode("1234"), "user", "도시탐험가", null),
                new Member(0, "user4", passwordEncoder.encode("1234"), "user", "호스트킹", null),
                new Member(0, "user5", passwordEncoder.encode("1234"), "user", "빠른토끼", null),
                new Member(0, "user6", passwordEncoder.encode("1234"), "user", "강남매니아", null),
                new Member(0, "user7", passwordEncoder.encode("1234"), "user", "맛집사냥꾼", null),
                new Member(0, "user8", passwordEncoder.encode("1234"), "user", "여행매니아", null),
                new Member(0, "user9", passwordEncoder.encode("1234"), "user", "프로여행가", null),
                new Member(0, "user10", passwordEncoder.encode("1234"), "user", "자유영혼", null),
                new Member(0, "user11", passwordEncoder.encode("1234"), "user", "고기러버", null),
                new Member(0, "user12", passwordEncoder.encode("1234"), "user", "호기심왕", null),
                new Member(0, "user13", passwordEncoder.encode("1234"), "user", "친절한집주인", null),
                new Member(0, "user14", passwordEncoder.encode("1234"), "user", "가을남자", null),
                new Member(0, "user15", passwordEncoder.encode("1234"), "user", "바다낚시꾼", null),
                new Member(0, "user16", passwordEncoder.encode("1234"), "user", "산악자전거", null),
                new Member(0, "user17", passwordEncoder.encode("1234"), "user", "숨은맛집찾기", null),
                new Member(0, "user18", passwordEncoder.encode("1234"), "user", "아침햇살", null),
                new Member(0, "user19", passwordEncoder.encode("1234"), "user", "커피애호가", null),
                new Member(0, "user20", passwordEncoder.encode("1234"), "user", "영화광", null),
                new Member(0, "host1", passwordEncoder.encode("1234"), "host", "달빛수호자", null),
                new Member(0, "host2", passwordEncoder.encode("1234"), "host", "불꽃사냥꾼", null),
                new Member(0, "host3", passwordEncoder.encode("1234"), "host", "서핑매니아", null),
                new Member(0, "host4", passwordEncoder.encode("1234"), "host", "드림캐쳐", null),
                new Member(0, "host5", passwordEncoder.encode("1234"), "host", "별빛전사", null),
                new Member(0, "host6", passwordEncoder.encode("1234"), "host", "고요한밤", null),
                new Member(0, "host7", passwordEncoder.encode("1234"), "host", "바람의전사", null),
                new Member(0, "host8", passwordEncoder.encode("1234"), "host", "심해탐험가", null),
                new Member(0, "host9", passwordEncoder.encode("1234"), "host", "숲속여행자", null),
                new Member(0, "host10", passwordEncoder.encode("1234"), "host", "낙엽사냥꾼", null),
                new Member(0, "host11", passwordEncoder.encode("1234"), "host", "대자연사랑꾼", null),
                new Member(0, "host12", passwordEncoder.encode("1234"), "host", "푸른바다", null),
                new Member(0, "host13", passwordEncoder.encode("1234"), "host", "번개추적자", null),
                new Member(0, "host14", passwordEncoder.encode("1234"), "host", "겨울왕국", null),
                new Member(0, "host15", passwordEncoder.encode("1234"), "host", "아침햇살", null),
                new Member(0, "host16", passwordEncoder.encode("1234"), "host", "따뜻한여름", null),
                new Member(0, "host17", passwordEncoder.encode("1234"), "host", "사막여우", null),
                new Member(0, "host18", passwordEncoder.encode("1234"), "host", "밤하늘지기", null),
                new Member(0, "host19", passwordEncoder.encode("1234"), "host", "숲속요정", null),
                new Member(0, "host20", passwordEncoder.encode("1234"), "host", "노을빛사냥꾼", null)
        );

        memberRepository.saveAll(members);
    }

    @Test
    void insertHost() {
        List<Host> hosts = Arrays.asList(
                new Host("host1", "$2a$10$lSgMzG5z13BjWuxe8/est./Ucmef2gGQiDR8ALYiBFDp.MtVBDh4e", "host1@example.com", "010-1234-5678", memberRepository.findById(118L).orElse(null), null, "달빛수호자"),
                new Host("host2", "$2a$10$.vv4s1enPAgtG3fDkt6kuO6s5WTde8XNecMioWlKBCtr1k4KrriIi", "host2@example.com", "010-2345-6789", memberRepository.findById(119L).orElse(null), null, "불꽃사냥꾼"),
                new Host("host3", "$2a$10$f62XwNSfX1SAxgScTxLtLOjVlwvP98Pc9hRMJWBGCYT6mIqmLTlt.", "host3@example.com", "010-3456-7890", memberRepository.findById(120L).orElse(null), null, "서핑매니아"),
                new Host("host4", "$2a$10$gy/0qw2SbtHwsm7YaX1IUeSHDLWpn1gPUn.3mNGhUDaXe9Vk.EZye", "host4@example.com", "010-4567-8901", memberRepository.findById(121L).orElse(null), null, "드림캐쳐"),
                new Host("host5", "$2a$10$VgCLQLEMuIQCLbBtXi4mkuAfLSp6oceLfjh8eQuyYQPeQC8wIPxva", "host5@example.com", "010-5678-9012", memberRepository.findById(122L).orElse(null), null, "별빛전사"),
                new Host("host6", "$2a$10$UoOPBlKvEb78UUrY1/4UPuWB.mFr5x/gc0sXR5OqqDOnHvu/WIq9G", "host6@example.com", "010-6789-0123", memberRepository.findById(123L).orElse(null), null, "고요한밤"),
                new Host("host7", "$2a$10$iXj.WLATwA3xej/0kZMaE.P4/RiqvPFzpLUmSDX8wRgiSqnuEdIKS", "host7@example.com", "010-7890-1234", memberRepository.findById(124L).orElse(null), null, "바람의전사"),
                new Host("host8", "$2a$10$62qtX3Kn86czRLkT746u2OECAFjM2SzA9oYjwfF7wp36EJSwlyfru", "host8@example.com", "010-8901-2345", memberRepository.findById(125L).orElse(null), null, "심해탐험가"),
                new Host("host9", "$2a$10$brSYVqFE3zkW4AFtXVgo2u.MNR6QdANe0d6NpzikYuaRYxpW0fH1S", "host9@example.com", "010-9012-3456", memberRepository.findById(126L).orElse(null), null, "숲속여행자"),
                new Host("host10", "$2a$10$trdKq6t/d2KLyonj7brCSexleEZhVlEUzQ2DWnvTjI0e3Tdl3VYE2", "host10@example.com", "010-0123-4567", memberRepository.findById(127L).orElse(null), null, "낙엽사냥꾼"),
                new Host("host11", "$2a$10$vGKDyKD9k59ODgvGcMu5Ae5l2IpZNzxYCsQzEnq0dpXb/cZecAAky", "host11@example.com", "010-1234-5678", memberRepository.findById(128L).orElse(null), null, "대자연사랑꾼"),
                new Host("host12", "$2a$10$3IaYLWO91jk/G9MtY2VFseppvqShmprbmRZBMbkcI6pwi5GHwcD9O", "host12@example.com", "010-2345-6789", memberRepository.findById(129L).orElse(null), null, "푸른바다"),
                new Host("host13", "$2a$10$VxdvCWT7kU9s.n.V8NfHxugqMcC14hk2TugpMh4OENCyHRYQb2KpS", "host13@example.com", "010-3456-7890", memberRepository.findById(130L).orElse(null), null, "번개추적자"),
                new Host("host14", "$2a$10$kLRwZcFgN01mxSzqjZBDyeAFOEUyKQTIenaLrNIzifs1wTHtQKSPW", "host14@example.com", "010-4567-8901", memberRepository.findById(131L).orElse(null), null, "겨울왕국"),
                new Host("host15", "$2a$10$d7.pbo9SmwEr0leDtWZ30eW3jcEQeE8lvc563mHssjg2KsNZu2/6y", "host15@example.com", "010-5678-9012", memberRepository.findById(132L).orElse(null), null, "아침햇살"),
                new Host("host16", "$2a$10$.ZNWi4HeLAXceVicyK2j7OLNH3uFX8/o.khcr0MxlWEbUOLBXHbUG", "host16@example.com", "010-6789-0123", memberRepository.findById(133L).orElse(null), null, "따뜻한여름"),
                new Host("host17", "$2a$10$.J8ULeF9FItL8C1pKqBOWuTvCaC7bkHlfpK6zPuRL0q9kjB20JU3C", "host17@example.com", "010-7890-1234", memberRepository.findById(134L).orElse(null), null, "사막여우"),
                new Host("host18", "$2a$10$ytmZKfcp/dnXX.3Wt/C5judeR9Vyswo//xNEsQv4E74mnxyrk/eCG", "host18@example.com", "010-8901-2345", memberRepository.findById(135L).orElse(null), null, "밤하늘지기"),
                new Host("host19", "$2a$10$U7uJB6fIQZn0Nc92o9JmZODgSzZC2GVJs1kRXikGum7PkxfY5hU5O", "host19@example.com", "010-9012-3456", memberRepository.findById(136L).orElse(null), null, "숲속요정"),
                new Host("host20", "$2a$10$3IPp6Qee881rn/w7KiTR6OEGObx8mrtoxROBb9/7Vb/uGNsc4xtxe", "host20@example.com", "010-0123-4567", memberRepository.findById(137L).orElse(null), null, "노을빛사냥꾼")
        );

        hostRepository.saveAll(hosts);
    }
}
