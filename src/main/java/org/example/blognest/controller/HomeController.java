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
    public String homePage(Model model) {
        model.addAttribute("title", "Home");
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

    @GetMapping("/posts/create")
    public String createPostPage(Model model) {
        model.addAttribute("title", "Create Post");
        return "create_post";
    }

    @GetMapping("/posts/drafts")
    public String draftsPage(Model model) {
        model.addAttribute("title", "Drafts");
        return "drafts";
    }
}
