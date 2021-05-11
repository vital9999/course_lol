package com.example.course_lol.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private int year;
    private int group_id;

    @OneToMany(mappedBy = "album", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Song> songs;

    public Album() {
    }

    public Album(String name, int year) {
        this.name = name;
        this.year = year;
        this.group_id = 1;
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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
    public int getMin(){
        int min=0;
        for(int i = 0;i<songs.size();i++){
            min = min + songs.get(i).getMin();
        }
        return min;
    }
}
