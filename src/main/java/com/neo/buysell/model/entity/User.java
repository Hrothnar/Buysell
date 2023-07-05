package com.neo.buysell.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    private String email;
    private String phone;
    @Column(name = "avatar_path")
    private String avatarPath;
    @OneToMany(targetEntity = Ad.class, cascade = {}, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private Set<Ad> ads = new HashSet<>();

    public User() {

    }

    public void addAd(Ad ad) {
        this.ads.add(ad); // больше "ad" богу "ad"
        ad.setUser(this);
    }

    public void addComment(Ad ad, Comment comment) { //опционально, может не понадобится
        ad.addComment(comment);
        addAd(ad);
    }

    public long getId() {
        return id;
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

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Set<Ad> getAds() {
        return ads;
    }

    public void setAds(Set<Ad> ads) {
        this.ads = ads;
    }
}
