package com.neo.buysell.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class TAd {
    @JsonProperty(value = "pk")
    private long adId;
    @JsonProperty(value = "author")
    private long authorId;
    @JsonProperty(value = "authorFirstName")
    private String firstName;
    @JsonProperty(value = "authorLastName")
    private String lastName;
    private String email;
    private String phone;
    @JsonProperty(value = "image")
    private String imagePath;
    private double price;
    private String title;
    private String description;


    public TAd() {

    }

    public TAd(long adId, long authorId, String firstName, String lastName, String email, String phone, String imagePath, double price, String title, String description) {
        this.adId = adId;
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.imagePath = imagePath;
        this.price = price;
        this.title = title;
        this.description = description;
    }

    public long getAdId() {
        return adId;
    }

    public void setAdId(long adId) {
        this.adId = adId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
