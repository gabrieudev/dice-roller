package com.api.diceRoller.service;

import com.api.diceRoller.dto.*;
import com.api.diceRoller.exception.EntityNotFoundException;
import com.api.diceRoller.exception.UserAlreadyExistsException;
import com.api.diceRoller.model.CheckerUser;
import com.api.diceRoller.model.User;
import com.api.diceRoller.repository.CheckerUserRepository;
import com.api.diceRoller.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CheckerUserRepository checkerUserRepository;

    /**
     * log in a user and return a JWT
     *
     * @param userLoginRequest request of user
     * @return response of user
     * @throws BadCredentialsException if any of the credentials are invalid
     */
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(userLoginRequest.email());

        userOptional.orElseThrow(
                () -> new BadCredentialsException("Email is invalid")
        );
        if (!bCryptPasswordEncoder.matches(userLoginRequest.password(), userOptional.get().getPassword())) {
            throw new BadCredentialsException("Password is invalid");
        }

        String token = tokenService.generateToken(userOptional.get());

        return new UserLoginResponse(token, 300L);
    }

    /**
     * register a new user and send confirmation email
     *
     * @param userDTO the user's DTO
     * @return the saved user
     * @throws UserAlreadyExistsException if the user already exists
     */
    public User register(UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = mappingService.toModel(userDTO);

        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Set.of("BASIC"));
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        user.setChecked(false);

        CheckerUser checkerUser = new CheckerUser();
        checkerUser.setUser(user);
        checkerUser.setExpiresAt(Instant.now().plusSeconds(600));

        User savedUser = userRepository.save(user);

        CheckerUser savedCheckerUser = checkerUserRepository.save(checkerUser);

        emailService.sendEmail(new Email(
                user.getEmail(),
                "Dice Roller email verification",
                "The code to verify your e-mail is " + savedCheckerUser.getId()));

        return savedUser;

    }

    /**
     * checks the id sent to the user's email
     *
     * @param userId the user's id
     * @param verificationId the verification's id
     * @throws EntityNotFoundException if user or checkerUser is not found
     * @throws AccessDeniedException if ids are different or code has expired
     */
    public void check(UUID userId, UUID verificationId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );

        CheckerUser checkerUser = checkerUserRepository.findByUser(user).orElseThrow(
                () -> new EntityNotFoundException("Code not send")
        );

        if (!verificationId.equals(checkerUser.getId())) {
            throw new AccessDeniedException("Invalid code");
        }
        if (checkerUser.getExpiresAt().isBefore(Instant.now())) {
            throw new AccessDeniedException("Code expired");
        }

        user.setChecked(true);
        checkerUserRepository.delete(checkerUser);
        userRepository.save(user);

    }

    /**
     * update user's password
     *
     * @param email the user's email
     * @param password the user's password
     * @param newPassword the user's new password
     * @return the updated user
     * @throws EntityNotFoundException if user not found
     * @throws BadCredentialsException if password is invalid
     */
    public User updatePassword(String email, String password, String newPassword) {

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Password is invalid");
        }

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        user.setUpdatedAt(Instant.now());

        return userRepository.save(user);

    }

    /**
     * retrieves all the users
     *
     * @return the List of users
     */
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(
                user -> mappingService.toDto(user)
        ).toList();
    }

}
