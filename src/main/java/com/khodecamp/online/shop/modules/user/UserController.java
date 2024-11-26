package com.khodecamp.online.shop.modules.user;

import com.khodecamp.online.shop.core.annotation.Body;
import com.khodecamp.online.shop.core.annotation.PageableParam;
import com.khodecamp.online.shop.core.request.PaginationRequest;
import com.khodecamp.online.shop.core.response.ResponseBuilder;
import com.khodecamp.online.shop.core.response.ResponseDto;
import com.khodecamp.online.shop.modules.user.dto.CreateUserDto;
import com.khodecamp.online.shop.modules.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User API")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users")
    @PreAuthorize("hasPermission('user', 'read')")
    public ResponseDto<List<User>> getAllUsers(@PageableParam PaginationRequest paginationRequest) {
        return ResponseBuilder.paginate(userService.getAllUsers(paginationRequest));
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseDto<User> getUserById(@PathVariable String username) {
        return ResponseBuilder.success(userService.getUserByUsername(username));
    }

    @PostMapping
    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public void createUser(@Body CreateUserDto user) {
        userService.createUser(user);
    }
}
