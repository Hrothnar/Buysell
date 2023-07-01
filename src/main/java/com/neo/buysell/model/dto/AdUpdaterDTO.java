package com.neo.buysell.model.dto;

import com.neo.buysell.model.entity.Ad;

public class AdUpdaterDTO {
    public double price;
    public String title;
    public String description;

    public AdUpdaterDTO() {

    }

    public static Ad toEntity(AdUpdaterDTO adUpdaterDTO) {
        Ad ad = new Ad();
        ad.setPrice(adUpdaterDTO.price);
        ad.setTitle(adUpdaterDTO.title);
        ad.setDescription(adUpdaterDTO.description);
        return ad;
    }

    public static AdUpdaterDTO toDto(Ad ad) {
        AdUpdaterDTO adUpdaterDTO = new AdUpdaterDTO();
        adUpdaterDTO.price = ad.getPrice();
        adUpdaterDTO.title = ad.getTitle();
        adUpdaterDTO.description = ad.getDescription();
        return adUpdaterDTO;
    }

}
