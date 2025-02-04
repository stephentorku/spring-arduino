package koco.cyber.spring_arduino.controller;


import koco.cyber.spring_arduino.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class OTPController {
    @Autowired
    private OTPService otpService;

    @PostMapping("/generate")
    public String generateOTP(@RequestParam String email) {
        String otp = otpService.generateOTP(email);
        return "OTP has been sent to your email.";
    }

    @PostMapping("/validate")
    public String validateOTP(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.validateOTP(email, otp);

        if (isValid) {
            return "OTP is valid!";
        } else {
            return "Invalid or expired OTP.";
        }
    }
}
