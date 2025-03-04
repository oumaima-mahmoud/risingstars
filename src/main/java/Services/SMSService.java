package Services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSService {

    // Twilio Account SID and Auth Token (replace these with your credentials from the Twilio dashboard)
    public static final String ACCOUNT_SID = "AC410c20c25c87eb2aff53aa6edc47a40c";  // Replace with your Twilio SID
    public static final String AUTH_TOKEN = "dc7106c0b10c1d5f11b4b9938cc4b834";    // Replace with your Twilio Auth Token

    // Constructor to initialize Twilio with your SID and Auth Token
    public SMSService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    /**
     * Sends a welcome message to the user.
     *
     * @param phoneNumber the recipient's phone number
     * @param nom         the user's last name
     * @param prenom      the user's first name
     */
    public void sendWelcomeMessage(String phoneNumber, String nom, String prenom) {
        try {
            // Creating and sending the message
            String message = "Bienvenue sur TuniStadium, " + nom + " " + prenom + "! Votre inscription a été réussie. Profitez de nos services et événements exclusifs !";
            Message.creator(
                    new PhoneNumber(phoneNumber),  // Recipient's phone number
                    new PhoneNumber("+16168983006"),  // Your Twilio phone number (the one you purchased)
                    message  // The message content
            ).create();
            System.out.println("Message sent successfully to " + phoneNumber);
        } catch (Exception e) {
            // Catching any exceptions and printing the error
            System.err.println("Error sending message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // For testing purposes: Call this method to send a test message
    public static void main(String[] args) {
        SMSService smsService = new SMSService();
        smsService.sendWelcomeMessage("+1234567890", "John", "Doe");
    }
}
