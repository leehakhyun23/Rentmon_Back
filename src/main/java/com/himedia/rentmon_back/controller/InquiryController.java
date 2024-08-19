package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.entity.Inquiry;
import com.himedia.rentmon_back.service.InquiryService;
import com.himedia.rentmon_back.util.PagingMj;
import lombok.RequiredArgsConstructor;
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

}
