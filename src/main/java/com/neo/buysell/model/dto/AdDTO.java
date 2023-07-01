package com.neo.buysell.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.buysell.model.entity.Ad;

public class AdDTO {
    @JsonProperty(value = "pk")
    public long id;
    @JsonProperty(value = "author")
    public long authorId;
    @JsonProperty(value = "image")
    public String imagePath;
    public double price;
    public String title;


    public AdDTO() {

    }

    public static Ad toEntity(AdDTO adDTO) {
        Ad ad = new Ad();
        ad.setAuthorId(adDTO.authorId);
        ad.setTitle(adDTO.title);
        ad.setPrice(adDTO.price);
        ad.setImagePath(adDTO.imagePath);
        return ad;
    }

    public static AdDTO toDto(Ad ad) {
        AdDTO adDTO = new AdDTO();
        adDTO.id = ad.getId();
        adDTO.authorId = ad.getAuthorId();
        adDTO.title = ad.getTitle();
        adDTO.price = ad.getPrice();
        adDTO.imagePath = ad.getImagePath();
        return adDTO;
    }
}
