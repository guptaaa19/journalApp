package com.example.journal.controller;


import com.example.journal.entity.User;
import com.example.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.saveEntry(user);
        return ResponseEntity.status(201).body("Entry created successfully");
    }
}
