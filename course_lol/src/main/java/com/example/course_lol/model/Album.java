package com.example.course_lol.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private int year;

    public Album() {
    }

    public Album(String name, int year) {
        this.name = name;
        this.year = year;
    }
}
