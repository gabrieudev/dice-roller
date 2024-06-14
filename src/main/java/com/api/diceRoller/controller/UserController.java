package com.api.diceRoller.controller;

import com.api.diceRoller.dto.UserDTO;
import com.api.diceRoller.service.TokenService;
import com.api.diceRoller.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserDTO userDTO) {
        userService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @PutMapping("/update-password/{email}/{password}/{newPassword}")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<Void> updatePassword(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("email") String email,
            @PathVariable("password") String password,
            @PathVariable("newPassword") String newPassword
    ) {
        if (tokenService.notBelongs(jwt, email)) {
            throw new AccessDeniedException("You don't have access to this");
        }
        userService.updatePassword(email, password, newPassword);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/check/{userId}/{verificationId}")
    public ResponseEntity<Void> check(
            @PathVariable("userId") UUID userId,
            @PathVariable("verificationId") UUID verificationId
    ) {
        userService.check(userId, verificationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
