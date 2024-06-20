package com.api.diceRoller.controller;

import com.api.diceRoller.dto.LoginDTO;
import com.api.diceRoller.dto.RegisterDTO;
import com.api.diceRoller.dto.TokenDTO;
import com.api.diceRoller.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class TokenController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return ResponseEntity.status(HttpStatus.OK).body("User registered successfully");
    }

}
