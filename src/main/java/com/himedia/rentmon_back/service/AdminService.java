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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final HostRepository hostRepository;
    private final CouponRepository couponRepository;
    private final DeclarationRepository declarationRepository;

    public Page<AdminDTO.ResponseUser> getUserList(Pageable pageable, String searchType, String keyword, Boolean isLogin, Boolean sortByDeclasCount) {
        Specification<User> spec = AdminSpecification.UserSpe.searchByUserList(searchType, keyword);

        if (isLogin != null) {
            spec = spec.and(AdminSpecification.UserSpe.filterByLoginStatus(isLogin));
        }

        List<User> users = userRepository.findAll(spec);

        if (Boolean.TRUE.equals(sortByDeclasCount)) {
            users.sort((u1, u2) -> {
                int declaCount1 = declarationRepository.countByUserAndSpaceIsNull(u1);
                int declaCount2 = declarationRepository.countByUserAndSpaceIsNull(u2);
                return Integer.compare(declaCount2, declaCount1);
            });
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), users.size());
        List<AdminDTO.ResponseUser> responseUsers = users.subList(start, end).stream()
                .map(user -> {
                    int declaCount = declarationRepository.countByUserAndSpaceIsNull(user);
                    return AdminDTO.ResponseUser.builder()
                            .userid(user.getUserid())
                            .name(user.getName())
                            .phone(user.getPhone())
                            .email(user.getEmail())
                            .createdAt(user.getCreatedAt())
                            .isLogin(user.isIslogin())
                            .gname(user.getGnum() != null ? user.getGnum().getGname() : null)
                            .declaCount(declaCount)
                            .build();
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responseUsers, pageable, users.size());
    }


    public int updateUserIsLoginStatus(List<String> userids) {
        return userRepository.updateIsLoginStatus(userids);
    }

    public Page<AdminDTO.ResponseHost> getHostList(Pageable pageable, String searchType, String keyword) {
        Specification<Host> spec = AdminSpecification.HostSpe.searchByHostList(searchType, keyword);
        Page<Host> hostList = hostRepository.findAll(spec, pageable);

        return hostList.map(host -> {
            List<AdminDTO.SpaceDTO> spaceDTOs = host.getSpaces().stream().map(space -> {
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
                        .disable(true)
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

    public Page<AdminDTO.ResponseCoupon> getCouponList(Pageable pageable) {
        return couponRepository.findAll(pageable)
                .map(coupon -> AdminDTO.ResponseCoupon.builder()
                        .couponStr(coupon.getCouponstr())
                        .title(coupon.getCouponTitle())
                        .discount(coupon.getDiscount())
                        .limitDateTime(coupon.getLimitdate())
                        .useYn(coupon.isUseyn())
                        .userid(coupon.getUser().getUserid())
                        .build()
                );
    }

    public Page<AdminDTO.ResponseCoupon> getCouponListByUseyn(Pageable pageable, Boolean useyn) {
        return couponRepository.findAllByUseyn(useyn, pageable)
                .map(coupon -> AdminDTO.ResponseCoupon.builder()
                        .couponStr(coupon.getCouponstr())
                        .title(coupon.getCouponTitle())
                        .discount(coupon.getDiscount())
                        .limitDateTime(coupon.getLimitdate())
                        .useYn(coupon.isUseyn())
                        .userid(coupon.getUser().getUserid())
                        .build()
                );
    }

//    public int updateHostIsLoginStatus(List<String> hostids) {
//        return hostRepository.updateIsLoginStatus(hostids);
//    }

    public Optional<Declaration> getDeclarationById(int dseq) {
        return declarationRepository.findById(dseq);
    }

    public AdminDTO.ResponseDeclaration getUserSpaceDeclarations(Pageable pageable) {
        Page<Declaration> declarationList = declarationRepository.findUserSpaceDeclarations(pageable);

        List<AdminDTO.DeclaUserSpace> userSpaceList = declarationList.stream()
                .map(declaration -> AdminDTO.DeclaUserSpace.builder()
                        .dseq(declaration.getDseq())
                        .title(declaration.getTitle())
                        .content(declaration.getContent())
                        .createdAt(declaration.getCreated_at())
                        .reply(declaration.getReply())
                        .replyDate(declaration.getReplydate())
                        .userid(declaration.getUser().getUserid())
                        .spaceTitle(declaration.getSpace().getTitle())
                        .build())
                .collect(Collectors.toList());

        return AdminDTO.ResponseDeclaration.builder()
                .userSpaceList(userSpaceList)
                .totalPages(declarationList.getTotalPages())
                .currentPage(declarationList.getNumber())
                .size(declarationList.getSize())
                .totalElements(declarationList.getTotalElements())
                .build();
    }

    public AdminDTO.ResponseDeclaration getHostUserDeclarations(Pageable pageable) {
        Page<Declaration> declarationList = declarationRepository.findHostUserDeclarations(pageable);

        List<AdminDTO.DeclaHostUser> hostUserList = declarationList.stream()
                .map(declaration -> AdminDTO.DeclaHostUser.builder()
                        .dseq(declaration.getDseq())
                        .title(declaration.getTitle())
                        .content(declaration.getContent())
                        .createdAt(declaration.getCreated_at())
                        .reply(declaration.getReply())
                        .replyDate(declaration.getReplydate())
                        .hostid(declaration.getHost().getHostid())
                        .userid(declaration.getUser().getUserid())
                        .build())
                .collect(Collectors.toList());

        return AdminDTO.ResponseDeclaration.builder()
                .hostUserList(hostUserList)
                .totalPages(declarationList.getTotalPages())
                .currentPage(declarationList.getNumber())
                .size(declarationList.getSize())
                .totalElements(declarationList.getTotalElements())
                .build();
    }
}
