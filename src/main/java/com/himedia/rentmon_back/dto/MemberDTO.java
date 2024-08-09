package com.himedia.rentmon_back.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter

public class MemberDTO extends User {

    private Integer mseq;
    private String userid;
    private String pwd;
    private String role;
    private Timestamp created_at;

    public MemberDTO(String username, String password , Timestamp created_at,
                  String role, int mseq) {
        super(username, password, List.of(new SimpleGrantedAuthority(role)));
        this.userid = username;
        this.pwd = password;
        this.role = role;
        this.created_at = created_at;
        this.mseq = mseq;
    }
    public Map<String, Object> getClaims(){
        Map<String, Object> datamap = new HashMap<>();
        datamap.put("mseq", mseq);
        datamap.put("userid", userid);
        datamap.put("pwd", pwd);
        datamap.put("role", role);
        datamap.put("created_at", created_at);
        return datamap;
    }

}
