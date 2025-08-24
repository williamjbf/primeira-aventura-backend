package github.williamjbf.primeiraaventura.user.controller;

import github.williamjbf.primeiraaventura.security.JwtService;
import github.williamjbf.primeiraaventura.user.dto.LoginRequestDTO;
import github.williamjbf.primeiraaventura.user.dto.LoginResponseDTO;
import github.williamjbf.primeiraaventura.user.dto.UserRequestDTO;
import github.williamjbf.primeiraaventura.user.dto.UserResponse;
import github.williamjbf.primeiraaventura.user.model.User;
import github.williamjbf.primeiraaventura.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
    public ResponseEntity<String> register(@Valid @RequestBody UserRequestDTO userRequest, HttpServletResponse response) {
        userService.createUser(userRequest);

        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername(userRequest.getUsername());
        loginRequest.setPassword(userRequest.getPassword());
        return this.login(loginRequest, response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtService.generateToken(userDetails);

        Cookie cookie = new Cookie("authToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true se estiver em HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 10); //10 hrs

        response.addCookie(cookie);

        return ResponseEntity.ok("Login efetuado com sucesso");
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUser(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();

        Cookie authCookie = Arrays.stream(cookies)
                .filter(c -> "authToken".equals(c.getName()))
                .findFirst()
                .orElse(null);

        String username = jwtService.extractUsername(authCookie.getValue());

        UserResponse user = this.userService.getUser(username);

        return ResponseEntity.ok(user);
    }
}
