package com.example.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {

        try {

            InputStream serviceAccount =
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream("firebase-key.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(serviceAccount)
                    )

                    // IMPORTANT
                    .setDatabaseUrl(
                            "https://demmo-172a7-default-rtdb.firebaseio.com/"
                    )

                    .build();

            if (FirebaseApp.getApps().isEmpty()) {

                FirebaseApp.initializeApp(options);
            }

            System.out.println("Firebase Initialized");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}