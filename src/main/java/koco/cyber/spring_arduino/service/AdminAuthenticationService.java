package koco.cyber.spring_arduino.service;

import koco.cyber.spring_arduino.entity.Administrator;
import koco.cyber.spring_arduino.repository.AdministratorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminAuthenticationService implements UserDetailsService {

    private final AdministratorRepository administratorRepository;

    public AdminAuthenticationService(AdministratorRepository administratorRepository){
        this.administratorRepository = administratorRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Administrator> administrator = administratorRepository.findByUsername(username);
        if (administrator.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(administrator.get().getUsername())
                .password(administrator.get().getPassword())
                .build();
    }
}
