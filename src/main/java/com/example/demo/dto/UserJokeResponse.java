package com.example.demo.dto;

import com.example.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserJokeResponse {

    private List<User> users;

    private String joke;
}