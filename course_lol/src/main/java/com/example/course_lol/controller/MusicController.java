package com.example.course_lol.controller;

import com.example.course_lol.model.Artist;
import com.example.course_lol.model.Group;
import com.example.course_lol.repo.ArtistRepository;
import com.example.course_lol.repo.GroupRepository;
import com.example.course_lol.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class MusicController {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private GroupRepository groupRepository;
    @GetMapping("/music")
    public String music(Model model){
        Artist artist = new Artist("Дмитрий", 1982);
        Group group = new Group("Дайте танк");
        List<Group> groupList = Arrays.asList(group);
        //artist.setGroup(groupList);
        artistRepository.save(artist);
        return "music";
    }

}
