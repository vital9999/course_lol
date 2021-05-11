package com.example.course_lol.model;

import javax.persistence.*;

@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    String name;
    String genre;
    private int min;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "album_id", referencedColumnName = "id")
    private Album album;

    public Song() {
    }


    public Song(String name, String genre) {
        this.name = name;
        this.genre = genre;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public Album getAlbum() {
        return album;
    }
}

