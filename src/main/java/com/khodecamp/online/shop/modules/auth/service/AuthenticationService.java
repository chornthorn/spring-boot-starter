package com.khodecamp.online.shop.modules.auth.service;


import com.khodecamp.online.shop.core.exception.UnauthorizedException;
import com.khodecamp.online.shop.core.service.JwtService;
import com.khodecamp.online.shop.modules.auth.dto.LoginRequest;
import com.khodecamp.online.shop.modules.auth.dto.LoginResponse;
import com.khodecamp.online.shop.core.common.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse authenticate(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            return new LoginResponse(
                    token,
                    userDetails.getUsername()
            );
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
}
