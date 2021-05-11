package com.example.course_lol.controller;

import com.example.course_lol.model.*;
import com.example.course_lol.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class MusicController {
//    @Autowired
//    private ArtistRepository artistRepository;
//    @Autowired
//    private GroupRepository groupRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @GetMapping("/music")
    public String music(Model model){
        Iterable<Album> albums = albumRepository.findAll();
        model.addAttribute("album", albums);
        return "music";
    }
    @GetMapping("/music/{id}")
    public String show(@PathVariable("id") int id, Model model){
        if(!albumRepository.existsById(id)){
            return "redirect:/music";
        }
        Optional<Album> album = albumRepository.findById(id);
        ArrayList<Album> res = new ArrayList<>();
        album.ifPresent(res::add);
        model.addAttribute("album", res);

        Iterable<Song> songs = songRepository.findByAlbum_id(id);
        model.addAttribute("songs", songs);
        return "showAlbum";
    }
    @GetMapping("/music/{id}/view")
    public String view(@PathVariable("id") int id, Model model){
        if(!albumRepository.existsById(id)){
            return "redirect:/music";
        }
        Optional<Album> album = albumRepository.findById(id);
        ArrayList<Album> res = new ArrayList<>();
        album.ifPresent(res::add);
        model.addAttribute("album", res);

        Iterable<Song> songs = songRepository.findByAlbum_id(id);
        model.addAttribute("songs", songs);
        return "view";
    }
    @GetMapping("/music/{id}/edit")
    public String albumEdit(@PathVariable("id")int id, Model model){
        Optional<Album> album = albumRepository.findById(id);
        ArrayList<Album> res = new ArrayList<>();
        album.ifPresent(res::add);
        model.addAttribute("album", res);
        return "editAlbum";
    }
    @PostMapping("/music/{id}/edit")
    public String AlbumPostUpdate(@PathVariable("id") int id, @RequestParam String name, @RequestParam int year){
        Album album = albumRepository.findById(id).orElseThrow();
        album.setName(name);
        album.setYear(year);
        albumRepository.save(album);
        return "redirect:/users";
    }
    @GetMapping("/music/{id}/new")
    public String addSongGet(@PathVariable("id") int id, Model model){
        Optional<Album> album = albumRepository.findById(id);
        ArrayList<Album> res = new ArrayList<>();
        album.ifPresent(res::add);
        model.addAttribute("album", res);
        return "addSong";
    }
    @PostMapping("/music/{id}/new")
    public String addSong(@PathVariable("id") int id, @RequestParam String name, @RequestParam String genre, @RequestParam int min){
        Album album = albumRepository.findById(id).orElseThrow();
        Song song = new Song();
        song.setGenre(genre);
        song.setName(name);
        song.setAlbum(album);
        song.setMin(min);

        songRepository.save(song);
        return "redirect:/music/" + id;
    }
    @PostMapping("/music/{id}/remove")
    public String removeAlbum(@PathVariable("id") int id){
        Album album = albumRepository.findById(id).orElseThrow();
        albumRepository.delete(album);
        return "redirect:/music";
    }

}
