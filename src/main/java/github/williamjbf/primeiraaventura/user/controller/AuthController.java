package github.williamjbf.primeiraaventura.user.controller;

import github.williamjbf.primeiraaventura.security.JwtUtil;
import github.williamjbf.primeiraaventura.user.dto.LoginRequestDTO;
import github.williamjbf.primeiraaventura.user.dto.LoginResponseDTO;
import github.williamjbf.primeiraaventura.user.dto.UserRequestDTO;
import github.williamjbf.primeiraaventura.user.model.User;
import github.williamjbf.primeiraaventura.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public LoginResponseDTO register(@Valid @RequestBody UserRequestDTO userRequest) {
        final String uncodedePassword = (userRequest.getPassword());
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(userRequest.toUser());

        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername(userRequest.getUsername());
        loginRequest.setPassword(uncodedePassword);
        return this.login(loginRequest);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        String token = jwtUtil.generateToken(authentication.getName());
        return new LoginResponseDTO(token);
    }

}
