package com.example.demo.service;

import com.example.demo.dto.JokeDto;
import com.example.demo.dto.SingleUserJokeResponse;
import com.example.demo.dto.UserJokeResponse;
import com.example.demo.model.User;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WebClient webClient;

    // SAVE USER
    public String saveUser(User user) {

        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference("users");

        databaseReference
                .child(user.getId())
                .setValueAsync(user);

        return "User Saved Successfully";
    }

    // GET ALL USERS + JOKE
    public UserJokeResponse getAllUsers()
            throws Exception {

        DatabaseReference reference =
                FirebaseDatabase.getInstance()
                        .getReference("users");

        CompletableFuture<List<User>> future =
                new CompletableFuture<>();

        List<User> users = new ArrayList<>();

        reference.addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        for (DataSnapshot data :
                                snapshot.getChildren()) {

                            User user =
                                    data.getValue(User.class);

                            users.add(user);
                        }

                        future.complete(users);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                        future.completeExceptionally(
                                error.toException()
                        );
                    }
                });

        List<User> userList = future.get();

        // CALL EXTERNAL JOKE API
        JokeDto jokeDto = webClient
                .get()
                .uri("/random_joke")
                .retrieve()
                .bodyToMono(JokeDto.class)
                .block();

        String fullJoke =
                jokeDto.getSetup()
                        + " "
                        + jokeDto.getPunchline();

        return new UserJokeResponse(
                userList,
                fullJoke
        );
    }
    public SingleUserJokeResponse getUserById(
            String id)
            throws Exception {

        DatabaseReference reference =
                FirebaseDatabase.getInstance()
                        .getReference("users")
                        .child(id);

        CompletableFuture<User> future =
                new CompletableFuture<>();

        reference.addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(
                            DataSnapshot snapshot) {

                        User user =
                                snapshot.getValue(User.class);

                        future.complete(user);
                    }

                    @Override
                    public void onCancelled(
                            DatabaseError error) {

                        future.completeExceptionally(
                                error.toException()
                        );
                    }
                });

        User user = future.get();

        // CALL JOKE API
        JokeDto jokeDto = webClient
                .get()
                .uri("/random_joke")
                .retrieve()
                .bodyToMono(JokeDto.class)
                .block();

        String fullJoke =
                jokeDto.getSetup()
                        + " "
                        + jokeDto.getPunchline();

        return new SingleUserJokeResponse(
                user,
                fullJoke
        );
    }
}