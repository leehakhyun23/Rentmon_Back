package com.himedia.rentmon_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ReportDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SpaceSalesData {
        private String spaceTitle; // 공간의 제목
        private int totalReservations; // 해당 공간의 총 예약 건수
        private int totalSales; // 해당 공간의 총 매출액
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseTotalSales {
        private List<SpaceSalesData> spaceSalesDataList; // 공간별 매출 데이터 리스트
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReservationDetail {
        private int payment;             // 결제 금액
        private Timestamp reservestart;  // 예약 시작 시간
        private Timestamp reserveend;    // 예약 종료 시간
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseReservations {
        private int totalReservations; // 총 예약 건수
        private List<ReservationDetail> reservationDetails; // 예약 세부 정보 리스트
    }
}
