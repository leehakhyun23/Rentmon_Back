package com.himedia.rentmon_back.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AdminDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDashBoard {
        private List<ResponseVisit> visit;
        private List<ResponseCategory> category;
        private ResponseMember member;
        private List<ResponseReservation> reservation;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseVisit {
        private int count;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Timestamp createdAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseReservation {
        private int payment;
        private int count;
        private String date;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseCategory {
        private String name;
        private Long value;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseMember {
        private int totalMember;
        private int userCount;
        private int hostCount;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseUser {
        private String userid;
        private String name;
        private String phone;
        private String email;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Timestamp createdAt;
        private int declaCount;
        private boolean isLogin;
        private String gname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseHost {
        private String hostid;
        private String nickname;
        private String phone;
        private String email;
        private List<SpaceDTO> spaces;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SpaceDTO {
        private String category;
        private String title;
        private Integer price;
        private String province;
        private String town;
        private String village;
        private String addressdetail;
        private int declaCount;
        private boolean disable;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RequestCoupon {
        private List<String> userids;
        private LocalDateTime limitDateTime;
        private Integer discount;
        private String couponTitle;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseCoupon {
        private String couponStr;
        private String title;
        private Integer discount;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private LocalDateTime limitDateTime;
        private boolean useYn;
        private String userid;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseDeclaration {
        List<DeclaUserSpace> userSpaceList;
        List<DeclaHostUser> hostUserList;
        private int totalPages;
        private int currentPage;
        private int size;
        private long totalElements;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DeclaUserSpace {
        private int dseq;
        private String title;
        private String content;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Timestamp createdAt;
        private String reply;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Timestamp replyDate;
        private String userid;
        private String spaceTitle;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DeclaHostUser {
        private int dseq;
        private String title;
        private String content;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Timestamp createdAt;
        private String reply;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Timestamp replyDate;
        private String hostid;
        private String userid;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReqeustDeclarationReply {
        private int dseq;
        private String reply;
        private LocalDateTime replyDate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseChatRoom {
        private int crseq;
//        private String profileImage;
        private String nickName;
        private String lastMessage;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Timestamp lastSendTime;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseChatMessage {
        private int cmseq;
        private String message;
        private String senderType;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Timestamp createdAt;
    }
}
