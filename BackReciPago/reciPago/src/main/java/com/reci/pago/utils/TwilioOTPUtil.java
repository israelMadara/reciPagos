package com.reci.pago.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class TwilioOTPUtil {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioNumeroTelefono;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public String sendOTP(String userTelefono) {
        String otp = String.format("%04d", (int) (Math.random() * 10000));
        Message.creator(
            new PhoneNumber("whatsapp:+521"+userTelefono),
            new PhoneNumber("whatsapp:" + twilioNumeroTelefono),
            "Tu código OTP para ReciPago es: " + otp
        ).create();
        return otp;
    }
}
