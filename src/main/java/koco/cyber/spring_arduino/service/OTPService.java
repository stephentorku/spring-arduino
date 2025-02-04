package koco.cyber.spring_arduino.service;

import koco.cyber.spring_arduino.model.OTPDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
public class OTPService {
    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(OTPService.class);


    // Store OTPs and their expiration times
    private HashMap<String, OTPDetails> otpData = new HashMap<>();

    private static final int OTP_VALID_DURATION = 5 * 60 * 1000; // 5 minutes in milliseconds

    public String generateOTP(String email) {
        // Generate a random 6-digit OTP
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        logger.info("OTP generated");
        // Store OTP with timestamp
        otpData.put(email, new OTPDetails(otp, System.currentTimeMillis()));

        // Send the OTP via email
        sendEmail(email, otp);
        return otp;
    }

    private void sendEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + ". It is valid for 5 minutes.");
        message.setFrom("your-email@gmail.com");

        mailSender.send(message);
        logger.info("Email sent to: "+ to);
    }

    public boolean validateOTP(String email, String otp) {
        if (!otpData.containsKey(email)) {
            return false;
        }

        OTPDetails details = otpData.get(email);

        // Check if the OTP matches and hasn't expired
        if (details.getOtp().equals(otp) &&
                (System.currentTimeMillis() - details.getTimestamp() <= OTP_VALID_DURATION)) {
            otpData.remove(email); // OTP is valid, remove it
            logger.info("OTP validated");
            return true;
        }

        logger.info("OTP is invalid/expired");
        return false;
    }
}
