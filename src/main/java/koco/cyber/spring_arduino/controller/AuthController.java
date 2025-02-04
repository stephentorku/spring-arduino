package koco.cyber.spring_arduino.controller;

import koco.cyber.spring_arduino.entity.Administrator;
import koco.cyber.spring_arduino.repository.AdministratorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AdministratorRepository administratorRepository, PasswordEncoder passwordEncoder) {
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Administrator administrator, Model model) {
        if (administratorRepository.findByUsername(administrator.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        administrator.setPassword(passwordEncoder.encode(administrator.getPassword())); // Encrypt password
        administratorRepository.save(administrator);

        return "redirect:/login";
    }

}
