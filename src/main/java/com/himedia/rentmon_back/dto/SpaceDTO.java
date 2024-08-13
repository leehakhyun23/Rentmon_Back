package com.himedia.rentmon_back.dto;

import com.himedia.rentmon_back.entity.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceDTO {
    //Space 테이블 조회

    private int sseq;
    private int price;
    private String title;
    private String subtitle;
    private String content;
    private int starttime;
    private int endtime;
    private String caution;
    private String zipcode;
    private String province;
    private String town;
    private String village;
    private String addressdetail;
    private int mintime;
    private int personnal;
    private int maxpersonnal;
    private Timestamp created_at;

    //ManyToOne으로 참조되는 데이터
    private String hostid;
    private int cnum;
    private int fnum;

    // spaceimage 테이블
    private List<String> imageNames;    //SpaceImage의 realName 목록

    public static SpaceDTO fromEntity(Space space) {
        SpaceDTO dto = new SpaceDTO();
        dto.setSseq(space.getSseq());
        dto.setAddressdetail(space.getAddressdetail());
        dto.setCaution(space.getCaution());
        dto.setContent(space.getContent());
        dto.setCreated_at(space.getCreated_at());
        dto.setEndtime(space.getEndtime());
        dto.setMaxpersonnal(space.getMaxpersonnal());
        dto.setMintime(space.getMintime());
        dto.setPersonnal(space.getPersonnal());
        dto.setPrice(space.getPrice());
        dto.setProvince(space.getProvince());
        dto.setStarttime(space.getStarttime());
        dto.setSubtitle(space.getSubtitle());
        dto.setTitle(space.getTitle());
        dto.setTown(space.getTown());
        dto.setVillage(space.getVillage());
        dto.setZipcode(space.getZipcode());


        dto.setCnum(space.getCategory() != null ? space.getCategory().getCnum() : 0);
        dto.setFnum(space.getFee() != null ? space.getFee().getFnum() : 0);
        dto.setHostid(space.getHost() != null ? space.getHost().getHostid() : null);

        return dto;
    }
}
