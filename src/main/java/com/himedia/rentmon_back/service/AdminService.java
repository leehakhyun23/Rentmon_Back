package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.AdminDTO;
import com.himedia.rentmon_back.entity.Coupon;
import com.himedia.rentmon_back.entity.Declaration;
import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.CouponRepository;
import com.himedia.rentmon_back.repository.DeclarationRepository;
import com.himedia.rentmon_back.repository.HostRepository;
import com.himedia.rentmon_back.repository.UserRepository;
import com.himedia.rentmon_back.specification.AdminSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final HostRepository hostRepository;
    private final CouponRepository couponRepository;
    private final DeclarationRepository declarationRepository;

    public Page<AdminDTO.ResponseUser> getUserList(Pageable pageable, String searchType, String keyword) {
        Specification<User> spec = AdminSpecification.UserSpe.searchByUserList(searchType, keyword);
        Page<User> userList = userRepository.findAll(spec, pageable);

        return userList.map(user -> {
            int declaCount = declarationRepository.countByUserAndSpaceIsNull(user);

            return AdminDTO.ResponseUser.builder()
                    .userid(user.getUserid())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .createdAt(user.getCreatedAt())
                    .isLogin(user.isLogin())
                    .gname(user.getGrade() != null ? user.getGrade().getGname() : null)
                    .declaCount(declaCount)
                    .build();
        });
    }

    public int updateUserIsLoginStatus(List<String> userids) {
        return userRepository.updateIsLoginStatus(userids);
    }

    public Page<AdminDTO.ResponseHost> getHostList(Pageable pageable, String searchType, String keyword) {
        Specification<Host> spec = AdminSpecification.HostSpe.searchByHostList(searchType, keyword);
        Page<Host> hostList = hostRepository.findAll(spec, pageable);

        return hostList.map(host -> {
            List<AdminDTO.SpaceDTO> spaceDTOs = host.getSpaces().stream().map(space -> {
                // 신고 횟수를 계산
                int declaCount = declarationRepository.countBySpaceAndHostIsNull(space);

                return AdminDTO.SpaceDTO.builder()
                        .category(space.getCategory() != null ? space.getCategory().getName() : null)
                        .title(space.getTitle())
                        .price(space.getPrice())
                        .province(space.getProvince())
                        .town(space.getTown())
                        .village(space.getVillage())
                        .addressdetail(space.getAddressdetail())
                        .declaCount(declaCount)
                        .build();
            }).collect(Collectors.toList());

            return AdminDTO.ResponseHost.builder()
                    .hostid(host.getHostid())
                    .nickname(host.getNickname())
                    .phone(host.getPhone())
                    .email(host.getEmail())
                    .spaces(spaceDTOs)
                    .build();
        });
    }

    public Page<Coupon> getCouponList(Pageable pageable) {
        return couponRepository.findAll(pageable);
    }


//    public int updateHostIsLoginStatus(List<String> hostids) {
//        return hostRepository.updateIsLoginStatus(hostids);
//    }

    public Page<AdminDTO.ResponseDeclaration> getDeclarationList(Pageable pageable) {
        Specification<Declaration> spec = AdminSpecification.DeclarationSpe.searchByDeclarationList("", "");
        Page<Declaration> declarationList = declarationRepository.findAll(spec, pageable);

        return declarationList.map(declaration -> AdminDTO.ResponseDeclaration.builder()
                .dseq(declaration.getDseq())
                .title(declaration.getTitle())
                .content(declaration.getContent())
                .createdAt(declaration.getCreated_at())
                .reply(declaration.getReply())
                .replyDate(declaration.getReplydate())
                .build());
    }
}
