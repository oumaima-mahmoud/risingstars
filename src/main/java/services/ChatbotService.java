package services;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class ChatbotService {
    private static final String API_URL = "https://api-inference.huggingface.co/models/facebook/blenderbot-400M-distill";
    private static final String API_KEY = "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"; // Replace with your Hugging Face API key
    private static final OkHttpClient client = new OkHttpClient();

    public String sendMessage(String userMessage) throws IOException {
        // Create JSON payload
        String json = String.format("{\"inputs\": \"%s\"}", userMessage);

        // Build the request
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        // Send the request and process the response
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Parse the JSON response
            String responseBody = response.body().string();
            JSONArray jsonArray = new JSONArray(responseBody); // Response is an array
            JSONObject firstObject = jsonArray.getJSONObject(0); // Get the first object in the array
            return firstObject.getString("generated_text"); // Extract the chatbot's response
        }
    }
}