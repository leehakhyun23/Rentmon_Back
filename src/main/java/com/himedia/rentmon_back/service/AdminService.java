package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.AdminDTO;
import com.himedia.rentmon_back.entity.Coupon;
import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.*;
import com.himedia.rentmon_back.specification.AdminSpecification;
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
    private final DeclarationRepository declarationRepository;
    private final InquiryRepository inquiryRepository;

    public Page<AdminDTO.ResponseUser> getUserList(Pageable pageable, String searchType, String keyword) {
        Specification<User> spec = AdminSpecification.UserSpe.searchByUserList(searchType, keyword);
        Page<User> userList = userRepository.findAll(spec, pageable);

        return userList.map(user -> AdminDTO.ResponseUser.builder()
                .userid(user.getUserid())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .isLogin(user.isLogin())
                .gname(user.getGrade() != null ? user.getGrade().getGname() : null)
                .build());
    }

    public int updateUserIsLoginStatus(List<String> userids) {
        return userRepository.updateIsLoginStatus(userids);
    }

    public Page<AdminDTO.ResponseHost> getHostList(Pageable pageable, String searchType, String keyword) {
        Specification<Host> spec = AdminSpecification.HostSpe.searchByHostList(searchType, keyword);
        Page<Host> hostList = hostRepository.findAll(spec, pageable);

        return hostList.map(host -> AdminDTO.ResponseHost.builder()
                .hostid(host.getHostid())
                .nickname(host.getNickname())
//                .category()
//                .title()
                .phone(host.getPhone())
                .email(host.getEmail())
//                .province()
//                .town()
//                .village()
//                .addressdetail()
                .build());
    }

    public Page<Coupon> getCouponList(Pageable pageable) {
        return couponRepository.findAll(pageable);
    }

//    public int updateHostIsLoginStatus(List<String> hostids) {
//        return hostRepository.updateIsLoginStatus(hostids);
//    }
}
