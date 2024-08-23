package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Inquiry;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.InquiryRepository;
import com.himedia.rentmon_back.util.PagingMj;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class InquiryService {
    private final InquiryRepository ir;

    public int getInqueryALlCount(String userid) {
        return ir.countByUserUserid(userid);
    }

    public List<Inquiry> getInqueryList(String userid, PagingMj paging) {
        Pageable pageable = PageRequest.of(paging.getCurrentPage()-1, paging.getRecordrow());
        Page<Inquiry> list = ir.getInqueryList(userid,pageable);
        return list.getContent();
    }

    public void insertInquiry(Map<String, String> data) {
        User user = new User();
        Inquiry inq = new Inquiry();
        user.setUserid(data.get("userid"));
        inq.setUser(user);
        inq.setTitle( data.get("title"));
        inq.setContent( data.get("content"));
        Space space = new Space();
        space.setSseq(Integer.parseInt(data.get("sseq")));
        inq.setSpace(space);
        ir.save(inq);
    }

    public int getInqueryHostALlCount(Integer sseq) {
        return ir.countBySpaceSseq(sseq);

    }

    public List<Inquiry> getInqueryHostList(Integer sseq, PagingMj paging) {
        Pageable pageable = PageRequest.of(paging.getCurrentPage()-1, paging.getRecordrow() , Sort.by("iseq").descending());
        Page<Inquiry> list = ir.findBySpaceSseq(sseq,pageable);
        return list.getContent();
    }

    public void deleteInquiry(Integer iseq) {
        ir.deleteById(iseq);
    }
}
