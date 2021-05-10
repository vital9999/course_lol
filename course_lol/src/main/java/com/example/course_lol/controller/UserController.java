package com.example.course_lol.controller;

import com.example.course_lol.model.User;
import com.example.course_lol.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class UserController {
    public static int userId;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String menu(){
        return "login";
    }
    @GetMapping("/main")
    public String main(Model model){
        Optional<User> us = userRepository.findById(userId);
        ArrayList<User> res = new ArrayList<>();
        us.ifPresent(res::add);
        model.addAttribute("user", res);
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

}
