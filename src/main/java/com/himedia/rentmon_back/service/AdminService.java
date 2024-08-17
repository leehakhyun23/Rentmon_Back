package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.HostRepository;
import com.himedia.rentmon_back.repository.InquiryRepository;
import com.himedia.rentmon_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final HostRepository hostRepository;
    private final InquiryRepository inquiryRepository;

    public Page<User> getUserList(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public int updateIsLoginStatus(List<String> userids) {
        return userRepository.updateIsLoginStatus(userids);
    }
}
