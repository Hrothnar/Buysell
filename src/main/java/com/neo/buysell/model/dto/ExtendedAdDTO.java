package com.neo.buysell.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.buysell.model.entity.Ad;
import com.neo.buysell.model.entity.User;

public class ExtendedAdDTO {
    @JsonProperty(value = "pk")
    public long id;
    @JsonProperty(value = "image")
    public String imagePath;
    public double price;
    public String title;
    public String description;
    @JsonProperty(value ="authorFirstName")
    public String firstName;
    @JsonProperty(value ="authorLastName")
    public String lastName;
    public String email;
    public String phone;

    private final static String GET_IMAGE_ENDPOINT = "/ads/%d/image";

    public ExtendedAdDTO() {

    }

    public static ExtendedAdDTO toDto(Ad ad) {
        ExtendedAdDTO extendedAdDTO = new ExtendedAdDTO();
        User user = ad.getUser();
        extendedAdDTO.id = ad.getId();
        extendedAdDTO.imagePath = String.format(GET_IMAGE_ENDPOINT, ad.getId());
        extendedAdDTO.price = ad.getPrice();
        extendedAdDTO.title = ad.getTitle();
        extendedAdDTO.description = ad.getDescription();
        extendedAdDTO.firstName = user.getFirstName();
        extendedAdDTO.lastName = user.getLastName();
        extendedAdDTO.email = user.getEmail();
        extendedAdDTO.phone = user.getPhone();
        return extendedAdDTO;
    }

}
