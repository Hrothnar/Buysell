package com.neo.buysell.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "image_path")
    private String imagePath;
    private double price;
    private String title;
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

    public void removeComment(Comment comment) {
        comment.setAd(null);
        this.comments.remove(comment);
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
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
