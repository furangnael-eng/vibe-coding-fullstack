package com.example.vibeapp.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "VibeApp");
        model.addAttribute("message", "Thymeleaf view engine is successfully integrated!");
        model.addAttribute("currentTime",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return "home";
    }

    @GetMapping("/api/hello")
    @ResponseBody
    public String hello() {
        return "Hello, VibeApp is running!";
    }
}
