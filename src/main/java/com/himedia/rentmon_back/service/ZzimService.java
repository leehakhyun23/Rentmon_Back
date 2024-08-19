package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Zzim;
import com.himedia.rentmon_back.repository.ZzimRepositroy;
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
public class ZzimService {
    private final ZzimRepositroy zr;

    public List<Zzim> getZzimList(String userid, int page) {
        Pageable pageable = PageRequest.of(page-1, 6);
        Page<Zzim> list = zr.getZzimList(userid ,pageable);
        return list.getContent();
    }

    public void removeZzim(int zseq) {
        Zzim zzim = zr.findById(zseq).get();
        zr.delete(zzim);
    }
}
