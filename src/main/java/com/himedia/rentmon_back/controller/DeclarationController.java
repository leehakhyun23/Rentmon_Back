package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.entity.Declaration;
import com.himedia.rentmon_back.service.DeclarationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/declaration")
@RequiredArgsConstructor
public class DeclarationController {

    private final DeclarationService declarationService;

    @PostMapping("/insertDeclaration")
    public ResponseEntity<String> insertDeclaration(@RequestBody Declaration declaration) {
        declarationService.insertDeclaration(declaration);
        return ResponseEntity.ok("ok");


    }


}
