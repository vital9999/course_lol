package com.example.course_lol.controller;

import com.example.course_lol.config.TxtPrint;
import com.example.course_lol.config.WriterTemplate;
import com.example.course_lol.model.Album;
import com.example.course_lol.model.Song;
import com.example.course_lol.model.User;
import com.example.course_lol.model.User_albums;
import com.example.course_lol.repo.AlbumRepository;
import com.example.course_lol.repo.SongRepository;
import com.example.course_lol.repo.UserRepository;
import com.example.course_lol.repo.User_albumsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        List<User_albums> usal = user_albumsRepository.findAllByUserId(userId);
        for(int i = 0;i<usal.size();i++) {
            if (usal.get(i).getAlbum_id() == id) {
                System.out.println("есть такой");
                return "redirect:/main";
            }
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
        Optional<User> us = userRepository.findById(userId);
        ArrayList<User> res = new ArrayList<>();
        us.ifPresent(res::add);
        model.addAttribute("user", res);
        return "viewMy";
    }
    @GetMapping("/main/addBal/{id}/pay")
    public String  pay(@PathVariable("id") int id){
        Optional<User> user = userRepository.findById(id);
        User user1 = user.get();
        if(user1.pay(5))
        userRepository.save(user1);
        return "redirect:/main";
    }
    @GetMapping("/main/{id}/playlist")
    public String getPlay(@PathVariable("id") int id, Model model){

        Optional<User> user = userRepository.findById(id);
        User user1 = user.get();
        if(user1.isPlus()){

            List<User_albums> usal = user_albumsRepository.findAllByUserId(userId);
            ArrayList<Integer> list = new ArrayList<>();
            ArrayList<Song> song = new ArrayList<>();
            for(int i = 0;i< usal.size();i++){
                list.add(usal.get(i).getAlbum_id());
                song.addAll(songRepository.findAllByAlbum_id(usal.get(i).getAlbum_id()));
            }
            Iterable<Album> albums = albumRepository.findAllById(list);
            model.addAttribute("album", albums);
            model.addAttribute("song", song);
            Optional<User> us = userRepository.findById(userId);
            ArrayList<User> res = new ArrayList<>();
            us.ifPresent(res::add);
            model.addAttribute("user", res);

            return "playlist";
        }
        else return "redirect:/main";
    }
    @GetMapping("/main/{id}/playlist/sort3")
    public String sort3(Model model, @RequestParam int min, @PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        User user1 = user.get();
        if(user1.isPlus()){

            List<User_albums> usal = user_albumsRepository.findAllByUserId(userId);
            ArrayList<Integer> list = new ArrayList<>();
            ArrayList<Song> song = new ArrayList<>();
            for(int i = 0;i< usal.size();i++){
                list.add(usal.get(i).getAlbum_id());
                song.addAll(songRepository.findAllByAlbum_id(usal.get(i).getAlbum_id()));

                //song.addAll(songRepository.sortSong(usal.get(i).getAlbum_id()));
            }
            for(int i = 0;i<song.size();i++){
                if(song.get(i).getMin()!=min){
                    song.remove(i);
                    i--;
                }
            }
            Iterable<Album> albums = albumRepository.findAllById(list);
            model.addAttribute("album", albums);
            model.addAttribute("song", song);
            Optional<User> us = userRepository.findById(userId);
            ArrayList<User> res = new ArrayList<>();
            us.ifPresent(res::add);
            model.addAttribute("user", res);

            return "playlist";
        }
        else return "redirect:/main";
    }
    @GetMapping("/main/{id}/dayPlaylist")
    public String dayPlay(@PathVariable("id") int id, Model model){

        List<Song> songs = songRepository.findAll();

        if(songs.size() == 0)return "redirect:/";
        int min = 1;
        int max = songs.size();
        int diff = max - min;
        Random random = new Random();
        int t;
        ArrayList<Song> song2 = new ArrayList<>();
        for(int j = 0;j< songs.size()/2;j++){
            t = random.nextInt(diff + 1);
            t += min;
            song2.add(songs.get(t-1));
        }
        model.addAttribute("songs",song2);
        Optional<User> us = userRepository.findById(userId);
        ArrayList<User> res = new ArrayList<>();
        us.ifPresent(res::add);
        model.addAttribute("user", res);
       return "dayPlaylist";
    }
    @GetMapping("/main/{id}/dayPlaylist/save")
    public String dayPlaySave(@PathVariable("id") int id, Model model){

        List<Album> album1 = albumRepository.findAll();
        String name = Integer.toString(album1.size()) + " album";
        Album album = new Album(name, 2021);
        albumRepository.save(album);


        return "redirect:/main";
    }
    @GetMapping("/music/print")
    public String delToday(Model model) throws ParseException, ParserConfigurationException, TransformerException {
        List<Album> albums = albumRepository.findAll();
        WriterTemplate writerTemplate = new TxtPrint();
        writerTemplate.getInfo(albums);
        return "redirect:/music";
    }
}
