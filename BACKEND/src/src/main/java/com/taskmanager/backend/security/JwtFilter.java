package com.taskmanager.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        try {
            String header = request.getHeader("Authorization");
            System.out.println("AUTH HEADER: " + header);

            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7).trim();
                System.out.println("TOKEN: " + token.substring(0, 20) + "...");

                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.getEmailFromToken(token);
                    System.out.println("EMAIL FROM TOKEN: " + email);

                    // ✅ Wrap email in a UserDetails object
                    UserDetails userDetails = User.builder()
                            .username(email)
                            .password("")
                            .authorities(new ArrayList<>())
                            .build();

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("AUTHENTICATION SET SUCCESSFULLY");
                } else {
                    System.out.println("TOKEN VALIDATION FAILED");
                }
            } else {
                System.out.println("NO BEARER TOKEN FOUND IN REQUEST");
            }
        } catch (Exception e) {
            System.out.println("JWT FILTER ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        chain.doFilter(request, response);
    }
}