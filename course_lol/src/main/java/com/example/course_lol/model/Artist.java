package com.example.course_lol.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    String name;
    int year;

    public Artist() {
    }

    public Artist(String name, int year) {
        this.name = name;
        this.year = year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}
