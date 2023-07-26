package com.neo.buysell.model.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "image_path", length = 64)
    private String imagePath;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false, length = 64)
    private String title;
    @Column(nullable = false, length = 512)
    private String description;
    @ManyToOne(targetEntity = User.class, cascade = {}, fetch = FetchType.LAZY)
    private User user;
    @OneToMany(targetEntity = Comment.class, cascade = {}, fetch = FetchType.LAZY, mappedBy = "ad", orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    public Ad() {

    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setAd(this);
    }

    public long getId() {
        return id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(comments);
    }

}
