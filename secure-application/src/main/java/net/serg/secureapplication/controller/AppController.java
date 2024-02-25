package net.serg.secureapplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/info")
    public String info() {
        return "Info page";
    }

    @GetMapping("/about")
    public String about() {
        return "About page";
    }
    
    @GetMapping("/admin")
    public String admin() {
        return "Admin page";
    }
    
}