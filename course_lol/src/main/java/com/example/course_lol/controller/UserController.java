package com.example.course_lol.controller;

import com.example.course_lol.model.Album;
import com.example.course_lol.model.Song;
import com.example.course_lol.model.User;
import com.example.course_lol.model.User_albums;
import com.example.course_lol.repo.AlbumRepository;
import com.example.course_lol.repo.SongRepository;
import com.example.course_lol.repo.UserRepository;
import com.example.course_lol.repo.User_albumsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class UserController {
    public static int userId;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private User_albumsRepository user_albumsRepository;

    @GetMapping("/")
    public String menu(){
        //clearAll();

//        Song song1 = new Song("Вы", "альтернатива");
//        Song song2 = new Song("Мы", "альтернатива");
//        Song song3 = new Song("Я", "альтернатива");
//
//        Album album = new Album("На вырост", 2018);
//        song1.setAlbum(album);
//        song2.setAlbum(album);
//        song3.setAlbum(album);
//        List<Song> songs = Arrays.asList(song1,song2,song3);
//        songRepository.saveAll(songs);


//        List<Song> songs = Arrays.asList(song1,song2, song3);
//
//        album.setSongs(songs);
//        albumRepository.save(album);
        return "login";
    }

    private void clearAll() {
        songRepository.deleteAll();
        albumRepository.deleteAll();
    }

    @GetMapping("/main")
    public String main(Model model){
        Optional<User> us = userRepository.findById(userId);
        ArrayList<User> res = new ArrayList<>();
        us.ifPresent(res::add);
        model.addAttribute("user", res);
        Iterable<Album> albums = albumRepository.findAll();
        model.addAttribute("album", albums);
        return "main";
    }
    @GetMapping("/menu")
    public String menuGet(){
        return "menu";
    }

    @PostMapping ("/logUser")
    String logUser(@RequestParam String login, @RequestParam String pass, Model model){
        int id;
        Iterable<User> users = userRepository.findAll();
        ArrayList<User> user = new ArrayList<>();
        user = (ArrayList<User>) users;
        for(int i = 0;i<user.size();i++){
            id = user.get(i).checkPass(login, pass);
            if(id == 1){
                userId = id;
                return "redirect:/menu";
            }
            else if(id > 1){
                userId = id;
                return "redirect:/main";
            }
        }
        return "redirect:/";
    }
    @PostMapping("register")
    String register(@RequestParam String login, @RequestParam String pass){
        User user = new User(login, pass, "user");
        userId = user.getId();
        userRepository.save(user);
        return "main";
    }
    @GetMapping("/users/{id}")
    public String show(@PathVariable("id") int id, Model model){
        if(!userRepository.existsById(id)){
            return "redirect:/users";
        }
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "show";
    }
    @GetMapping("/users")
    public String showAll(Model model){
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/users/{id}/edit")
    public String userEdit(@PathVariable("id")int id, Model model){
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "edit";
    }
    @PostMapping("/users/{id}/edit")
    public String userPostUpdate(@PathVariable("id") int id, @RequestParam String login, @RequestParam String password){
        User user = userRepository.findById(id).orElseThrow();
        user.setRole("user");
        user.setLogin(login);
        user.setPassword(password);

        userRepository.save(user);
        return "redirect:/users";
    }
    @GetMapping("/users/new")
    public String userCreate(){
        return "add";
    }
    @PostMapping("/users/new")
    public String userPostCreate(@RequestParam String login, @RequestParam String password){
        User user = new User(login,password,"user");
        userRepository.save(user);
        return "redirect:/users";
    }
    @PostMapping("/users/{id}/remove")
    public String userDelete(@PathVariable("id") int id){
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/users";
    }
    @GetMapping("/main/addBal/{id}")
    public String addBal(Model model, @PathVariable("id") int id){
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "editUs";
    }
    @PostMapping("/main/addBal/{id}")
    public String userPosUpdate(@PathVariable("id") int id, @RequestParam String login, @RequestParam String password, @RequestParam double balance){
        User user = userRepository.findById(id).orElseThrow();
        user.setRole("user");
        user.setLogin(login);
        user.setPassword(password);
        user.setBalance(balance);
        if(balance > 5){
            user.setPlus(true);
        }

        userRepository.save(user);
        return "redirect:/main";
    }
    @GetMapping("/music/{id}/addUs")
    public String view(@PathVariable("id") int id, Model model){
        if(!albumRepository.existsById(id)){
            return "redirect:/music";
        }
        Optional<Album> albumO = albumRepository.findById(id);
        Album album = albumO.get();

        User_albums user_albums = new User_albums();
        user_albums.setAlbum_id(album.getId());
        user_albums.setUser_id(userId);
        user_albums.setDate(new Date());
        user_albumsRepository.save(user_albums);
        return "redirect:/main";
    }
    @GetMapping("/main/viewMy")
    public String myMusic(Model model){
        List<User_albums> usal = user_albumsRepository.findAllByUserId(userId);
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0;i< usal.size();i++){
            list.add(usal.get(i).getAlbum_id());
        }
        Iterable<Album> albums = albumRepository.findAllById(list);
        model.addAttribute("album", albums);
        return "viewMy";
    }
}
