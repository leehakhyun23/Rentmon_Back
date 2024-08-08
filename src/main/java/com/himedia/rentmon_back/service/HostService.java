package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HostService {

    @Autowired
    SpaceRepository sr;

}
