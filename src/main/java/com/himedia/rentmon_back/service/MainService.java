package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MainService {
    @Autowired
    private final UserRepository ur;

    public MainService(UserRepository ur) {
        this.ur = ur;
    }

}
