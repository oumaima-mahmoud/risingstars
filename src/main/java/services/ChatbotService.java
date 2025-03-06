package services;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import entite.Reclamation;
import java.util.List;

public class ChatbotService {
    private static final String API_URL = "https://api-inference.huggingface.co/models/facebook/blenderbot-400M-distill";
    private static final String API_KEY = "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"; // Replace with your Hugging Face API key
    private static final OkHttpClient client = new OkHttpClient();

    private ReclamationService reclamationService;

    public ChatbotService() {
        this.reclamationService = new ReclamationService(); // Initialize the reclamation service
    }

    public String sendMessage(String userMessage) throws IOException {
        // Add context about your project and database
        String context = "You are a helpful assistant for a reclamation management system. " +
                "The system allows users to submit, view, and manage reclamations (complaints). " +
                "Reclamations are stored in a SQL database and have the following attributes: " +
                "ID, User ID, Type (e.g., Application, Finance, Commande), Description, Object, State (e.g., en_attente, en_cours, termine), Date, and Phone Number. " +
                "You can answer questions about reclamations, users, types, states, and how to use the system. " +
                "If you don't know the answer, say 'I don't know.'";

        // Handle specific queries
        if (userMessage.toLowerCase().contains("how many reclamations")) {
            int totalReclamations = reclamationService.getAll(new Reclamation()).size();
            return "There are currently " + totalReclamations + " reclamations in the database.";
        }

        if (userMessage.toLowerCase().contains("status of reclamation")) {
            try {
                // Extract the reclamation ID from the message (e.g., "status of reclamation 123")
                String[] parts = userMessage.split(" ");
                int reclamationId = Integer.parseInt(parts[parts.length - 1]);

                // Create a Reclamation object and set the ID
                Reclamation reclamation = new Reclamation(); // Use the no-argument constructor
                reclamation.setId(reclamationId); // Set the ID

                // Query the database for the reclamation
                Reclamation result = reclamationService.getOne(reclamation);
                if (result != null) {
                    return "The status of reclamation #" + reclamationId + " is: " + result.getEtat();
                } else {
                    return "Reclamation #" + reclamationId + " not found.";
                }
            } catch (NumberFormatException e) {
                return "Please provide a valid reclamation ID (e.g., 'status of reclamation 123').";
            }
        }

        if (userMessage.toLowerCase().contains("list reclamation types")) {
            List<String> types = reclamationService.getAllReclamationTypes();
            return "The available reclamation types are: " + String.join(", ", types);
        }

        if (userMessage.toLowerCase().contains("list reclamation states")) {
            List<String> states = reclamationService.getAllReclamationStates();
            return "The available reclamation states are: " + String.join(", ", states);
        }

        if (userMessage.toLowerCase().contains("reclamations by user")) {
            try {
                // Extract the user ID from the message (e.g., "reclamations by user 123")
                String[] parts = userMessage.split(" ");
                int userId = Integer.parseInt(parts[parts.length - 1]);

                // Query the database for reclamations by user ID
                List<Reclamation> reclamations = reclamationService.getReclamationsByUserId(userId);
                if (!reclamations.isEmpty()) {
                    StringBuilder response = new StringBuilder("Reclamations for user #" + userId + ":\n");
                    for (Reclamation rec : reclamations) {
                        response.append("- ID: ").append(rec.getId())
                                .append(", Type: ").append(rec.getType())
                                .append(", State: ").append(rec.getEtat())
                                .append("\n");
                    }
                    return response.toString();
                } else {
                    return "No reclamations found for user #" + userId + ".";
                }
            } catch (NumberFormatException e) {
                return "Please provide a valid user ID (e.g., 'reclamations by user 123').";
            }
        }

        if (userMessage.toLowerCase().contains("reclamations by state")) {
            // Extract the state from the message (e.g., "reclamations by state en_attente")
            String[] parts = userMessage.split(" ");
            String state = parts[parts.length - 1];

            // Query the database for reclamations by state
            List<Reclamation> reclamations = reclamationService.getReclamationsByState(state);
            if (!reclamations.isEmpty()) {
                StringBuilder response = new StringBuilder("Reclamations in state '" + state + "':\n");
                for (Reclamation rec : reclamations) {
                    response.append("- ID: ").append(rec.getId())
                            .append(", User ID: ").append(rec.getUserId())
                            .append(", Type: ").append(rec.getType())
                            .append("\n");
                }
                return response.toString();
            } else {
                return "No reclamations found in state '" + state + "'.";
            }
        }

        if (userMessage.toLowerCase().contains("how to add a reclamation")) {
            return "To add a reclamation, use the 'Ajouter Réclamation' button in the application. " +
                    "You will need to provide the following details: " +
                    "User ID, Type (e.g., Application, Finance, Commande), Description, Object, State (e.g., en_attente, en_cours, termine), and Phone Number.";
        }

        if (userMessage.toLowerCase().contains("how to view reclamations")) {
            return "To view reclamations, use the 'View Reclamations' feature in the application. " +
                    "You can filter reclamations by type, state, or search for specific keywords.";
        }

        // Default behavior: Send the message to the chatbot
        String prompt = context + "\n\nUser: " + userMessage;

        // Create JSON payload
        String json = String.format("{\"inputs\": \"%s\"}", prompt);

        // Build the request
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        // Send the request and process the response
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                // Handle API errors
                if (response.code() == 503) {
                    return "Bot: The chatbot service is currently unavailable. Please try again later.";
                } else {
                    return "Bot: Sorry, I couldn't process your request. Error code: " + response.code();
                }
            }

            // Parse the JSON response
            String responseBody = response.body().string();
            JSONArray jsonArray = new JSONArray(responseBody);

            if (jsonArray.length() > 0) {
                JSONObject firstObject = jsonArray.getJSONObject(0);
                if (firstObject.has("generated_text")) {
                    return firstObject.getString("generated_text");
                }
            }
            return "Bot: Sorry, I couldn't generate a response.";
        }
    }
}