package com.himedia.rentmon_back.controller;


import com.himedia.rentmon_back.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/host")
public class HostController {

    @Autowired
    HostService hs;


}
