//package com.pjt.petmily.global.FCM;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Configuration
//public class FirebaseConfig {
//    @Bean
//    public FirebaseApp firebaseApp() throws IOException {
//        FileInputStream serviceAccountFile = new FileInputStream("resources/google-services.json");
//        FirebaseOptions options = FirebaseOptions
//                .builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccountFile))
//                .build();
//        return FirebaseApp.initializeApp(options);
//    }
//}