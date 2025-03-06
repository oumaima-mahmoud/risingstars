package services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioService {

    // Replace these with your Twilio credentials
    private static final String ACCOUNT_SID = "AC54c5ce43debaf0e2fda9230be1057e4c";
    private static final String AUTH_TOKEN = "c4cf8282493ee91b310687069eb3b1c0";
    private static final String TWILIO_PHONE_NUMBER = "+15102414079";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendSMS(String toPhoneNumber, String messageBody) {
        try {
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber), // Recipient's phone number
                    new PhoneNumber(TWILIO_PHONE_NUMBER), // Your Twilio phone number
                    messageBody // SMS content
            ).create();

            System.out.println("SMS sent! SID: " + message.getSid());
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoi du SMS : " + e.getMessage());
        }
    }
}