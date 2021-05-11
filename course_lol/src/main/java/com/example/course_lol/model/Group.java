package com.example.course_lol.model;

import javax.persistence.*;

@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    String name;
    private int artist_id;


    public Group() {
        artist_id = 1;
    }


    public Group(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
