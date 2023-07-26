package com.neo.buysell.model.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true, length = 64)
    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
