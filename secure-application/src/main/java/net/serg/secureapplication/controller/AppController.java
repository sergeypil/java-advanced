package net.serg.secureapplication.controller;

import net.serg.secureapplication.security.AdminAccess;
import net.serg.secureapplication.security.PublicAccess;
import net.serg.secureapplication.security.UserAccess;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @UserAccess
    @GetMapping("/info")
    public String info() {
        return "Info page";
    }

    @PublicAccess
    @GetMapping("/about")
    public String about() {
        return "About page";
    }
    
    @AdminAccess
    @GetMapping("/admin")
    public String admin() {
        return "Admin page";
    }
    
}