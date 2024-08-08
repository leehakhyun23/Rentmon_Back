package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository ur;
    public User getUserInfo(String userid) {
        return ur.findByUserid(userid);
    }
}
