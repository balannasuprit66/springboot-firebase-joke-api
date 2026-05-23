package com.example.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {

        try {

            String firebaseKey =
                    System.getenv("firebase-key.json");

            byte[] decoded =
                    Base64.getDecoder()
                            .decode(firebaseKey);

            InputStream serviceAccount =
                    new ByteArrayInputStream(decoded);

            FirebaseOptions options =
                    FirebaseOptions.builder()
                            .setCredentials(
                                    GoogleCredentials
                                            .fromStream(serviceAccount)
                            )
                            .setDatabaseUrl(
                                    "https://demmo-172a7-default-rtdb.firebaseio.com/"
                            )
                            .build();

            if (FirebaseApp.getApps().isEmpty()) {

                FirebaseApp.initializeApp(options);

                System.out.println(
                        "Firebase Initialized Successfully"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}