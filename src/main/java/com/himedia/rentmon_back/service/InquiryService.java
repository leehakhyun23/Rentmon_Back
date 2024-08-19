package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Inquiry;
import com.himedia.rentmon_back.repository.InquiryRepository;
import com.himedia.rentmon_back.util.PagingMj;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class InquiryService {
    private final InquiryRepository ir;

    public int getInqueryALlCount(String userid) {
        return ir.findByUseridCount(userid);
    }

    public List<Inquiry> getInqueryList(String userid, PagingMj paging) {
        Pageable pageable = PageRequest.of(paging.getCurrentPage()-1, paging.getRecordrow());
        Page<Inquiry> list = ir.getInqueryList(userid,pageable);
        return list.getContent();
    }
}
