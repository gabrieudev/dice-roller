package com.api.diceRoller.config.data_loader;

import com.api.diceRoller.model.User;
import com.api.diceRoller.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Configuration
@Transactional
public class AdminDataLoader implements CommandLineRunner {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String emailAdmin = "admin@gmail.com";
        Optional<User> userOptional = userRepository.findByEmail(emailAdmin);
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setEmail(emailAdmin);
            String passwordAdmin = "admin";
            user.setPassword(bCryptPasswordEncoder.encode(passwordAdmin));
            user.setRoles(Set.of("ADMIN", "BASIC"));
            user.setCreatedAt(Instant.now());
            user.setUpdatedAt(Instant.now());
            user.setName("admin");
            user.setChecked(true);
            userRepository.save(user);
        }
    }
}
