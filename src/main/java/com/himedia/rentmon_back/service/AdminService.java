package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Coupon;
import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.CouponRepository;
import com.himedia.rentmon_back.repository.HostRepository;
import com.himedia.rentmon_back.repository.InquiryRepository;
import com.himedia.rentmon_back.repository.UserRepository;
import com.himedia.rentmon_back.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final HostRepository hostRepository;
    private final CouponRepository couponRepository;
    private final InquiryRepository inquiryRepository;

    public Page<User> getUserList(Pageable pageable, String searchType, String keyword) {
        Specification<User> spec = UserSpecification.searchByUserList(searchType, keyword);

        return userRepository.findAll(spec, pageable);
    }

    public int updateUserIsLoginStatus(List<String> userids) {
        return userRepository.updateIsLoginStatus(userids);
    }

    public List<Host> getHostList() {
        return hostRepository.findAll();
    }

    public Page<Coupon> getCouponList(Pageable pageable) {
        return couponRepository.findAll(pageable);
    }

//    public int updateHostIsLoginStatus(List<String> hostids) {
//        return hostRepository.updateIsLoginStatus(hostids);
//    }
}
