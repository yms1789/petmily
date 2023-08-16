package com.pjt.petmily.global.FCM;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
//import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/petmily-2d449/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, String title, String body, Long boardId) throws IOException {
        String message = makeMessage(targetToken, title, body, boardId);
        System.out.println("FCM sendMessageTo");

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    private String makeMessage(String targetToken, String title, String body, Long boardId) throws JsonParseException, JsonProcessingException {
        System.out.println("FCM makeMessageTo");

        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build())
                        .data(FcmMessage.Data.builder()
                                .boardId(String.valueOf(boardId))
                                .build())
                        .build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
      // 로컬 경로
//      String firebaseConfigPath = "petmily-2d449-firebase-adminsdk-n5bdz-41c8b28c42.json";
        // 서버 경로
        File currentDirectory = new File(new File(".").getAbsolutePath());
        String path = currentDirectory.getCanonicalPath();

        System.out.println(path);

        String firebaseConfigPath = "resources/firebase/petmily-2d449-firebase-adminsdk-n5bdz-41c8b28c42.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new FileInputStream(firebaseConfigPath))
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        System.out.println("FIREBASE GET ACCESS TOKEN");
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
