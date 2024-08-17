package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.AdminHostDTO;
import com.himedia.rentmon_back.entity.Inquiry;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.HostRepository;
import com.himedia.rentmon_back.repository.InquiryRepository;
import com.himedia.rentmon_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
//    private final DeclarationRepository declarationRepository;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public int updateIsLoginStatus(List<String> userids) {
        return userRepository.updateIsLoginStatus(userids);
    }

//    public List<AdminHostDTO> getHostList() {
//        return hostRepository.findHostBySpaceJpql();
//    }

//    public List<DeclarationDTO> getDeclarationList() {
//        return declarationRepository.findAllByDeclaration();
//    }

    public List<Inquiry> getInquiryList() {
        return inquiryRepository.findAll();
    }



//    public Optional<Declaration> getDeclaration(int dseq) {
//        return declarationRepository.findById(dseq);
//    }
}
