package com.example.demo.controller;

import com.example.demo.dto.SingleUserJokeResponse;
import com.example.demo.dto.UserJokeResponse;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public String saveUser(
            @RequestBody User user) {

        return userService.saveUser(user);
    }
    @GetMapping
    public UserJokeResponse getAllUsers()
            throws Exception {

        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public SingleUserJokeResponse getUserById(
            @PathVariable String id)
            throws Exception {

        return userService.getUserById(id);
    }
}