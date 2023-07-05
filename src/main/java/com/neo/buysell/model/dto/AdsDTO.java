package com.neo.buysell.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AdsDTO {
    public int count;
    @JsonProperty(value = "results")
    public List<AdDTO> adDTOList;

    public AdsDTO() {

    }

    public AdsDTO(int count, List<AdDTO> adDTOList) {
        this.count = count;
        this.adDTOList = adDTOList;
    }
}
