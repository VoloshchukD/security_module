package com.epam.esm.controller.jwt;

import com.epam.esm.entity.User;
import com.epam.esm.entity.dto.UserDetailsDto;
import com.epam.esm.service.util.ExceptionMessageHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private SecretKey secretKey;

    private JwtConfiguration jwtConfiguration;

    public static final String AUTHORITIES = "authorities";

    public static final String AUTHORITY = "authority";

    public JwtTokenVerifier(SecretKey secretKey, JwtConfiguration jwtConfiguration) {
        this.secretKey = secretKey;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtConfiguration.getAuthorizationHeader());
        if (authorizationHeader == null
                || !authorizationHeader.startsWith(jwtConfiguration.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(jwtConfiguration.getTokenPrefix(), "");
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String username = body.getSubject();

            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get(AUTHORITIES);
            User.Role userRole = authorities.stream()
                    .map(m -> User.Role.parseString(m.get(AUTHORITY)))
                    .findFirst().get();
            User user = new User();
            user.setId(Long.valueOf(body.getId()));
            user.setUsername(username);
            user.setRole(userRole);
            UserDetails userDetails = new UserDetailsDto(user);

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get(AUTHORITY)))
                    .collect(Collectors.toSet());
            Authentication authentication
                    = new UsernamePasswordAuthenticationToken(userDetails, null, simpleGrantedAuthorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {

            String messageName = ExceptionMessageHandler.getMessageForLocale(
                    ExceptionMessageHandler.STRANGE_TOKEN_MESSAGE_NAME,
                    request.getLocale());
            throw new IllegalStateException(String.format(messageName, token));
        }
        filterChain.doFilter(request, response);
    }

}
