package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.entity.Inquiry;
import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.service.InquiryService;
import com.himedia.rentmon_back.service.SpaceService;
import com.himedia.rentmon_back.util.PagingMj;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/inquery")
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService is;
    private final SpaceService ss;

    @GetMapping("/getInqueryList/{userid}")
    public ResponseEntity<Map<String, Object>> getInqueryList(@PathVariable String userid , @RequestParam("page") int page) {
        Map<String, Object> map = new HashMap<>();
        PagingMj paging = new PagingMj();
        paging.setCurrentPage(page);
        paging.setRecordAllcount(is.getInqueryALlCount(userid));
        List<Inquiry> list = is.getInqueryList(userid,paging);
        map.put("list", list);
        map.put("paging", paging);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/insertInquiry")
    public ResponseEntity<String> insertInquiry(@RequestBody Map<String, String> data){
        is.insertInquiry(data);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/getInqueryListToHost/{sseq}")
    public ResponseEntity<Map<String, Object>> getInqueryListToHost(@PathVariable Integer sseq , @RequestParam("page") int page) {
        Map<String, Object> map = new HashMap<>();
        PagingMj paging = new PagingMj();
        paging.setCurrentPage(page);
        paging.setRecordAllcount(is.getInqueryHostALlCount(sseq));
        List<Inquiry> list = is.getInqueryHostList(sseq,paging);
        map.put("list", list);
        map.put("paging", paging);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/delete/{iseq}")
    public ResponseEntity<String> delete(@PathVariable Integer iseq) {
        is.deleteInquiry(iseq);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/igetsseq")
    public ResponseEntity<List<Inquiry>> getSseqsByHostid(@RequestParam("hostid") String hostid) {
        try {
            System.out.println("여기까진 되눈중~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            List<Integer> sseqs = ss.findSseqsByHostid(hostid);
            System.out.println(sseqs + "--------------------------인쿼리 관련이세요 ------------------------------------------------");
            List<Inquiry> inquirys = is.findInquirysBysseq(sseqs);
            // 여기부분에 sseqs 과 관련된 모든 예약정보를 찾는 코드
            System.out.println("아아아아아아아아아아아아아ㅏㅏㅣㅓㅙㅣㅑㅙㅔㅑ"+inquirys);
            return ResponseEntity.ok(inquirys);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
}
