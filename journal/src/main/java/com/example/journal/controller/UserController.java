package com.example.journal.controller;


import com.example.journal.entity.JournalEntry;
import com.example.journal.entity.User;
import com.example.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> entries = userService.getAll();
        return ResponseEntity.ok(entries);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.saveEntry(user);
        return ResponseEntity.status(201).body("Entry created successfully");
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        User userIdDb = userService.findByUserName(userName);
        if(userIdDb != null) {
            userIdDb.setUserName(user.getUserName());
            userIdDb.setPassword(user.getPassword());
            userService.saveEntry(userIdDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
