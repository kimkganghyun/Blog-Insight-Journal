package org.example.blognest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String showProfilePage(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "profile";
    }
}
