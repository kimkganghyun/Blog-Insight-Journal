package org.example.blognest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        return "register";
    }

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("title", "Posts");
        return "posts";
    }

    @GetMapping("/posts/new")
    public String newPost(Model model) {
        model.addAttribute("title", "New Post");
        return "newPost";
    }
}
