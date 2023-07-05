package com.neo.buysell.model.dto;

import java.util.List;

public class AdsDTO {
    public int count;
    public List<AdDTO> adDTOList;

    public AdsDTO() {

    }

    public AdsDTO(int count, List<AdDTO> adDTOList) {
        this.count = count;
        this.adDTOList = adDTOList;
    }
}
