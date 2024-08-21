package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.entity.Zzim;
import com.himedia.rentmon_back.repository.SpaceRepository;
import com.himedia.rentmon_back.repository.UserRepository;
import com.himedia.rentmon_back.repository.ZzimRepositroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ZzimService {
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;
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

    public boolean checkZzim(String userId, int sseq) {
        return zr.existsByUser_UseridAndSpace_Sseq(userId, sseq);

    }

    public void toggleZzim(String userid, int sseq) {

        User user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Space space = spaceRepository.findById(sseq)
                .orElseThrow(() -> new RuntimeException("Space not found"));

        boolean exists = zr.existsByUserAndSpace(user, space);

        if (exists) {
            zr.deleteByUserAndSpace(user, space);
        } else {
            Zzim zzim = new Zzim();
            zzim.setUser(user);
            zzim.setSpace(space);
            zr.save(zzim);
        }

    }
}
