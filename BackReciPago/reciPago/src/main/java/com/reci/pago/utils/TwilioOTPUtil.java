package com.reci.pago.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class TwilioOTPUtil {
	
	
	@Value("${twilio.phone-number}")
    private String twilioNumeroTelefono;

    public String sendOTP(String userTelefono) {
        String otp = String.format("%04d", (int) (Math.random() * 10000));
        Message.creator(
            new PhoneNumber("whatsapp:+" + "+5217151644812"),
            new PhoneNumber("whatsapp:+" + twilioNumeroTelefono),
            "Tu código OTP para ReciPago es: " + otp
        ).create();
        return otp;
    }
	

}
