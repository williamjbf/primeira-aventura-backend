package github.williamjbf.primeiraaventura.security;

import github.williamjbf.primeiraaventura.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, @Lazy UserService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String jwt;
        final String username;

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            filterChain.doFilter(request, response); // não tem cookie, segue normalmente (rota pública)
            return;
        }

        Cookie authCookie = Arrays.stream(cookies)
                .filter(c -> "authToken".equals(c.getName()))
                .findFirst()
                .orElse(null);

        if (authCookie == null) {
            filterChain.doFilter(request, response); // sem cookie, segue normalmente
            return;
        }

        jwt = authCookie.getValue();

        try {
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            filterChain.doFilter(request, response); // token inválido, segue sem autenticar
            return;
        }

        // Verifica se usuário não está autenticado ainda
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
