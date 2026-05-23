package com.example.demo.dto;

import com.example.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SingleUserJokeResponse {

    private User user;

    private String joke;
}