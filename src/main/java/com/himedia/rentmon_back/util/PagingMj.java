package com.himedia.rentmon_back.util;

import lombok.Data;

@Data
public class PagingMj {
    // 현재 페이지
    private int currentPage = 1;
    // 총 레코드 수
    private int recordAllcount; // 46
    // 총 페이지 숫자
    private int pageAllcount; // 10
    // 페이지네이션 첫 페이지 숫자
    private int firstnum;
    // 페이지네이션 끝 숫자
    private int lastnum;
    // 보여질 레코드 개수
    private int recordrow = 5;
    // 화면에 보여질 페이지네이션 개수
    private int offsetnum;
    private int pagecnt = 5;

    private boolean next;
    private boolean prev;

    private void pageMethod() {
        // 페이지 오프셋 계산
        offsetnum = (currentPage - 1) * recordrow;

        // 총 페이지 수 계산
        pageAllcount = (int) Math.ceil((double) recordAllcount / recordrow);

        // 페이지네이션의 첫 번째 페이지 번호 계산
        firstnum = ((currentPage - 1) / pagecnt) * pagecnt + 1;

        // 페이지네이션의 마지막 페이지 번호 계산
        lastnum = firstnum + pagecnt - 1;

        // 마지막 페이지 번호가 총 페이지 수를 넘지 않도록 조정
        if (lastnum > pageAllcount) {
            lastnum = pageAllcount;
        }

        // 이전 그룹으로 이동 가능한지 여부 설정
        prev = firstnum > 1;

        // 다음 그룹으로 이동 가능한지 여부 설정
        next = lastnum < pageAllcount;

        // 디버깅용 출력 (필요시 주석 처리)
        System.out.println("Prev: " + prev);
        System.out.println("Next: " + next);
        System.out.println("Page Count: " + pageAllcount);
    }

    public void setRecordAllcount(int recordAllcount) {
        this.recordAllcount = recordAllcount;
        pageMethod();
    }
}
