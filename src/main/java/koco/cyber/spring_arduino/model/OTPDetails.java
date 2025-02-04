package koco.cyber.spring_arduino.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class OTPDetails {
    private String otp;
    private long timestamp;
}
