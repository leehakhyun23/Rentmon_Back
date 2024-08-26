package com.himedia.rentmon_back;

import com.himedia.rentmon_back.entity.*;
import com.himedia.rentmon_back.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootTest
class RentmonBackApplicationTests {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private SpaceRepository spaceRepository;
    @Autowired
    private DeclarationRepository declarationRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private VisitRepository visitRepository;

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
    void insertUser() {
        Random random = new Random();
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            String username = "user" + (i + 1);
            Member member = memberRepository.findById(98L + i).orElse(null);

            // Generate random bankId between 1 and 18
            int bankId = random.nextInt(18) + 1;

            // Retrieve the bank if it exists, otherwise create a new one
//            Bank bank = bankRepository.findById(bankId).orElse(new Bank(bankId, "BankName"));

            // Generate random 16-digit card number
            String cardNumber = String.format("%04d-%04d-%04d-%04d",
                    random.nextInt(10000),
                    random.nextInt(10000),
                    random.nextInt(10000),
                    random.nextInt(10000));

            // Generate random expiration date in MMYY format
            String expirationDate = String.format("%02d%02d",
                    random.nextInt(12) + 1,  // Month between 01 and 12
                    random.nextInt(10) + 25); // Year between 25 and 34

            // Generate random 3-digit CVC code
            int cvc = random.nextInt(900) + 100;

            // Create Card object
//            Card card = new Card(0, bank, cardNumber, expirationDate, cvc);

            // Save the Card object to get a persistent reference
//            card = cardRepository.save(card);

            // Create Grade object (assuming Grade needs to be persisted)
            Grade grade = new Grade(1, "bronze", 1000);

            // Create User and add to the list
//            User user = new User(username, member, card, grade, null, "", "", null, null, null, null, null, null, null);
//            users.add(user);
        }

        // Save all users to the repository
        userRepository.saveAll(users);
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

    @Test
    void insertDeclation() {
        Random random = new Random();
        List<Declaration> declarations = new ArrayList<>();

        // 예시로 사용할 신고 제목 및 내용
        String[] userToSpaceTitles = {
                "공간 청결 상태 불만",
                "예약이 제대로 되지 않았습니다",
                "시설 고장이 있었습니다",
                "냉난방이 제대로 되지 않았습니다",
                "사진과 다른 공간"
        };

        String[] userToSpaceContents = {
                "공간이 너무 더러워서 사용하기 힘들었습니다.",
                "예약 확인이 되지 않아 불편을 겪었습니다.",
                "이용 중 시설 고장이 있어 불편했습니다.",
                "냉방이 전혀 되지 않아 더웠습니다.",
                "사진과 실물이 너무 달라 실망했습니다."
        };

        String[] hostToUserTitles = {
                "사용 후 뒷정리 불량",
                "약속된 시간을 지키지 않음",
                "공간을 손상시켰습니다",
                "규칙을 지키지 않았습니다",
                "무단으로 공간을 사용했습니다"
        };

        String[] hostToUserContents = {
                "사용 후 공간이 매우 지저분했습니다.",
                "예약한 시간에 오지 않아 다른 손님이 불편했습니다.",
                "공간의 일부가 손상되어 수리가 필요합니다.",
                "정해진 규칙을 무시하고 공간을 사용했습니다.",
                "예약된 시간 외에도 공간을 사용했습니다."
        };

        // Generate 30 declarations for user -> space
        for (int i = 0; i < 30; i++) {
            String title = userToSpaceTitles[random.nextInt(userToSpaceTitles.length)];
            String content = userToSpaceContents[random.nextInt(userToSpaceContents.length)];

            User user = userRepository.findById("user" + (random.nextInt(20) + 1)).orElse(null);
            Space space = spaceRepository.findById((int) (67L + random.nextInt(71))).orElse(null); // Random space between 67 and 137

            if (user != null && space != null) {
                Declaration declaration = new Declaration();
                declaration.setTitle(title);
                declaration.setContent(content);
                declaration.setUser(user);
                declaration.setSpace(space);
                declarations.add(declaration);
            }
        }

        // Generate 30 declarations for host -> user
        for (int i = 0; i < 30; i++) {
            String title = hostToUserTitles[random.nextInt(hostToUserTitles.length)];
            String content = hostToUserContents[random.nextInt(hostToUserContents.length)];

            Host host = hostRepository.findById("host" + (random.nextInt(20) + 1)).orElse(null);
            User user = userRepository.findById("user" + (random.nextInt(20) + 1)).orElse(null); // Random user between user1 and user20

            if (host != null && user != null) {
                Declaration declaration = new Declaration();
                declaration.setTitle(title);
                declaration.setContent(content);
                declaration.setHost(host);
                declaration.setUser(user);
                declarations.add(declaration);
            }
        }

        // Save all declarations to the repository
        declarationRepository.saveAll(declarations);
    }

    @Test
    void insertReservation() {
        Random random = new Random();

        for (int i = 0; i < 300; i++) {
            Reservation reservation = new Reservation();

            // User와 Space를 랜덤으로 선택
            User user = userRepository.findById("user" + (random.nextInt(20) + 1)).orElse(null);
            Space space = spaceRepository.findById(random.nextInt(71) + 67).orElse(null);

            // 4년 전부터 현재까지의 랜덤한 날짜 생성
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = now.minusYears(4).plusDays(random.nextInt((int) ChronoUnit.DAYS.between(now.minusYears(4), now)));
            LocalDateTime end = start.plusHours(random.nextInt(48)); // 예약 기간을 1시간에서 48시간 사이로 설정

            reservation.setUser(user);
            reservation.setSpace(space);
            reservation.setPayment(random.nextInt(50000) + 5000); // 5000원에서 50000원 사이의 랜덤 금액
            reservation.setRequest("요청사항 " + i);
            reservation.setReservestart(Timestamp.valueOf(start));
            reservation.setReserveend(Timestamp.valueOf(end));

            reservationRepository.save(reservation);
        }
    }

    @Test
    @Transactional
    public void updateCreatedAtForReservations() {
        Random random = new Random();
        ZoneId zone = ZoneId.of("Asia/Seoul");

        for (int i = 40; i <= 341; i++) {
            Reservation reservation = reservationRepository.findById(i).orElse(null);
            if (reservation != null) {
                // 1년 전부터 현재까지의 날짜를 랜덤으로 생성
                LocalDateTime start = LocalDateTime.now().minusYears(1);
                LocalDateTime end = LocalDateTime.now();
                long startEpochMilli = start.atZone(zone).toInstant().toEpochMilli();
                long endEpochMilli = end.atZone(zone).toInstant().toEpochMilli();

                // 랜덤한 날짜와 시간을 생성
                long randomEpochMilli = startEpochMilli + (long) (random.nextDouble() * (endEpochMilli - startEpochMilli));
                Timestamp randomTimestamp = new Timestamp(randomEpochMilli);

                // 생성한 날짜를 created_at 필드에 설정하여 업데이트
                reservation.setCreated_at(randomTimestamp);
                reservationRepository.save(reservation);
            }
        }
    }

    @Test
    public void generateTestVisits() {
        Random random = new Random();

        for (int i = 0; i < 3000; i++) {
            Visit visit = new Visit();

            // 랜덤 IP 주소 생성
            String ipAddress = random.nextInt(256) + "." +
                    random.nextInt(256) + "." +
                    random.nextInt(256) + "." +
                    random.nextInt(256);

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime randomDate = now.minusDays(random.nextInt(365));

            visit.setIpAddress(ipAddress);
            visit.setCreatedAt(Timestamp.valueOf(randomDate));

            visitRepository.save(visit);
        }
    }
}
