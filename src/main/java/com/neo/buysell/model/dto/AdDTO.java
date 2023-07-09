package com.neo.buysell.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.buysell.model.entity.Ad;

import java.text.MessageFormat;

public class AdDTO {
    @JsonProperty(value = "pk")
    public long id;
    @JsonProperty(value = "author")
    public long authorId;
    @JsonProperty(value = "image")
    public String imagePath;
    public double price;
    public String title;

    private final static String GET_IMAGE_ENDPOINT = "/ads/%d/image";

    public AdDTO() {

    }

    public static AdDTO toDto(Ad ad) {
        AdDTO adDTO = new AdDTO();
        adDTO.id = ad.getId();
        adDTO.authorId = ad.getUser().getId();
        adDTO.title = ad.getTitle();
        adDTO.price = ad.getPrice();
        adDTO.imagePath = String.format(GET_IMAGE_ENDPOINT, ad.getId());
        return adDTO;
    }
}
