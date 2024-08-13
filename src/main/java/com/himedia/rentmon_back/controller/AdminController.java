//package com.himedia.rentmon_back.controller;
//
//import com.himedia.rentmon_back.dto.AdminHostDTO;
//import com.himedia.rentmon_back.dto.DeclarationDTO;
//import com.himedia.rentmon_back.entity.Inquiry;
//import com.himedia.rentmon_back.entity.User;
//import com.himedia.rentmon_back.service.AdminService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/admin")
//@RequiredArgsConstructor
//public class AdminController {
//    @Autowired
//    private final AdminService adminService;
//
//    @GetMapping("/user")
//    public ResponseEntity<List<User>> getUserList() {
//        List<User> userList = adminService.getUserList();
//
//        if(userList.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.ok(userList);
//    }
//
////    @GetMapping("/host")
////    public ResponseEntity<List<AdminHostDTO>> getHostList() {
////        List<AdminHostDTO> hostList = adminService.getHostList();
////
////        if (hostList.isEmpty()) {
////            return ResponseEntity.noContent().build();
////        }
////
////        return ResponseEntity.ok(hostList);
////    }
//
//    @GetMapping("/inquiry")
//    public ResponseEntity<List<Inquiry>> getInquiryList() {
//        List<Inquiry> inquiryList = adminService.getInquiryList();
//
//        if (inquiryList.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.ok(inquiryList);
//    }
//
//    @GetMapping("/declaration")
//    public ResponseEntity<List<DeclarationDTO>> getDeclaration() {
////        List<DeclarationDTO> declarationList = adminService.getDeclarationList();
////
////        if(declarationList.isEmpty()) {
////            return ResponseEntity.noContent().build();
////        }
////
////        return ResponseEntity.ok(declarationList);
//        return ResponseEntity.ok(null);
//    }
//
////    @GetMapping("/declarationview/{dseq}")
////    public ResponseEntity<Declaration> getDeclaration(@PathVariable("dseq") int dseq) {
////        Optional<Declaration> optional = adminService.getDeclaration(dseq);
////
////        if (optional.isPresent()) {
////            Declaration declaration = optional.get();
////
////            return ResponseEntity.ok(declaration);
////        } else {
////            return ResponseEntity.noContent().build();
////        }
////    }
//}
