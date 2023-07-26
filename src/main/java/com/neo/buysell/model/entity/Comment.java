package com.neo.buysell.model.entity;

import javax.persistence.*;

@Entity(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "creation_time", nullable = false)
    private long creationTime;
    @Column(nullable = false, length = 1024)
    private String text;
    @ManyToOne(targetEntity = Ad.class, cascade = {}, fetch = FetchType.LAZY)
    private Ad ad;

    public Comment() {

    }

    public long getId() {
        return id;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
}
