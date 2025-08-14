package github.williamjbf.primeiraaventura.user.controller;

import github.williamjbf.primeiraaventura.security.JwtService;
import github.williamjbf.primeiraaventura.user.dto.LoginRequestDTO;
import github.williamjbf.primeiraaventura.user.dto.LoginResponseDTO;
import github.williamjbf.primeiraaventura.user.dto.UserRequestDTO;
import github.williamjbf.primeiraaventura.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public LoginResponseDTO register(@Valid @RequestBody UserRequestDTO userRequest) {
        userService.createUser(userRequest);

        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername(userRequest.getUsername());
        loginRequest.setPassword(userRequest.getPassword());
        return this.login(loginRequest);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtService.generateToken(userDetails);

        return new LoginResponseDTO(token);
    }

}
