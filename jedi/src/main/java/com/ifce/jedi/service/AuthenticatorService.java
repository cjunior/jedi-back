package com.ifce.jedi.service;

import com.ifce.jedi.dto.Authenticator.AuthenticatorDto;
import com.ifce.jedi.dto.Authenticator.TokenValidationResult;
import com.ifce.jedi.exception.custom.TokenExpiredException;
import com.ifce.jedi.exception.custom.TokenNotFoundException;
import com.ifce.jedi.infra.security.TokenService;
import com.ifce.jedi.model.User.PasswordResetToken;
import com.ifce.jedi.model.User.User;
import com.ifce.jedi.repository.PasswordResetTokenRepository;
import com.ifce.jedi.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticatorService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    @Value("${app.register-url}")
    private String baseUrl;

    public String login(AuthenticatorDto dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public void forgotPassword(@Email @NotBlank String email) {
        Optional<User> userOptional = userRepository.findByLogin(email)
                .map(userDetails -> (User) userDetails);

        if (userOptional.isEmpty()) {
            return;
        }

        var user = userOptional.get();
        var token = UUID.randomUUID().toString();

        var resetTokenOptional = passwordResetTokenRepository.findByUser(user);

        PasswordResetToken resetToken = resetTokenOptional.orElseGet(() ->
                new PasswordResetToken()
        );

        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));

        passwordResetTokenRepository.save(resetToken);

        String link = baseUrl + "/auth/" + token;

        emailService.sendPasswordResetEmail(
                user.getLogin(),
                link
        );
    }


    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token inválido ou expirado"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token expirado!");
        }

        User user = resetToken.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }

    public TokenValidationResult checkToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElse(null);

        if (resetToken == null) {
            return new TokenValidationResult(false, "Token inválido ou não encontrado", null);
        }

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return new TokenValidationResult(false, "Token expirado", resetToken.getExpiryDate());
        }

        return new TokenValidationResult(true, "Token válido", resetToken.getExpiryDate());
    }

}
