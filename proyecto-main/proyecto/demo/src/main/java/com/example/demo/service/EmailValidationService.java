package com.example.demo.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailValidationService {

    @Value("${zerobounce.api.key}")
    private String ZERO_BOUNCE_API_KEY;

    private static final String ZERO_BOUNCE_API_URL = "https://api.zerobounce.net/v2/validate";

    public boolean isEmailValid(String email) {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                .url(ZERO_BOUNCE_API_URL + 
                     "?api_key=" + ZERO_BOUNCE_API_KEY + 
                     "&email=" + email + 
                     "&ip_address=")
                .get()
                .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("API call failed: " + response);
                }

                String jsonResponse = response.body().string();
                Gson gson = new Gson();
                JsonObject responseObject = gson.fromJson(jsonResponse, JsonObject.class);

                // Lógica de validación
                String status = responseObject.get("status").getAsString();
                return "valid".equalsIgnoreCase(status) || 
                       "catch-all".equalsIgnoreCase(status);
            }
        } catch (Exception e) {
            // Log the error
            System.err.println("Email validation error: " + e.getMessage());
            // En caso de error, permitimos el registro para no bloquear usuarios
            return true;
        }
    }
}