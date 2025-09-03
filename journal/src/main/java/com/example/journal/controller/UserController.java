package com.example.journal.controller;


import com.example.journal.entity.JournalEntry;
import com.example.journal.entity.User;
import com.example.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userIdDb = userService.findByUserName(userName);
        userIdDb.setUserName(user.getUserName());
        userIdDb.setPassword(user.getPassword());
        userService.saveEntry(userIdDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
