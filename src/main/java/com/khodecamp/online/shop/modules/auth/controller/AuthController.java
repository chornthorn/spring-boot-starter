package com.khodecamp.online.shop.modules.auth.controller;

import com.khodecamp.online.shop.modules.auth.dto.LoginRequest;
import com.khodecamp.online.shop.modules.auth.dto.LoginResponse;
import com.khodecamp.online.shop.modules.auth.dto.UserRegistrationDto;
import com.khodecamp.online.shop.modules.auth.dto.UserResponse;
import com.khodecamp.online.shop.modules.auth.service.AuthenticationService;
import com.khodecamp.online.shop.modules.user.UserService;
import com.khodecamp.online.shop.modules.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegistrationDto request) {
        User user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.fromUser(user));
    }
}
