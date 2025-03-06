package controllers.reclamation;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.ChatbotService;

public class ChatboxController {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;

    private ChatbotService chatbotService;

    public ChatboxController() {
        this.chatbotService = new ChatbotService(); // Initialize the chatbot service
    }

    @FXML
    private void handleSendMessage() {
        String userMessage = messageField.getText();
        if (!userMessage.isEmpty()) {
            chatArea.appendText("You: " + userMessage + "\n");
            messageField.clear();

            try {
                // Send the user message to the chatbot and get the response
                String botResponse = chatbotService.sendMessage(userMessage);
                chatArea.appendText("Bot: " + botResponse + "\n");
            } catch (Exception e) {
                chatArea.appendText("Bot: Sorry, I couldn't process your request.\n");
                e.printStackTrace();
            }
        }
    }
}