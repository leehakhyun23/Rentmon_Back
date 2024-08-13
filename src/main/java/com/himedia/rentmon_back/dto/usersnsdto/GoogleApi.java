package com.himedia.rentmon_back.dto.usersnsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GoogleApi {
    private String id;

    private String email;

    private String verified_email;

    private String name;

    private String given_name;

    private String family_name;

    private String picture;





}
