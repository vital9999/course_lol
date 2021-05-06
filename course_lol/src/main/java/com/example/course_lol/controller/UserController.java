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
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String menu(){
        return "menu";
    }
    @GetMapping("/users/{id}")
    public String show(@PathVariable("id") int id, Model model){
        if(!userRepository.existsById(id)){
            return "redirect:/blog";
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


}
