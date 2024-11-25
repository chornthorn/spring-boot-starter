package com.khodecamp.online.shop.modules.user;

import com.khodecamp.online.shop.core.annotation.Body;
import com.khodecamp.online.shop.core.annotation.PageableParam;
import com.khodecamp.online.shop.core.request.PageRequest;
import com.khodecamp.online.shop.core.response.ResponseBuilder;
import com.khodecamp.online.shop.core.response.ResponseDto;
import com.khodecamp.online.shop.modules.user.dto.CreateUserDto;
import com.khodecamp.online.shop.modules.user.dto.UserSpecial;
import com.khodecamp.online.shop.modules.user.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseDto<List<User>> getAllUsers(@PageableParam PageRequest pageRequest) {
        return ResponseBuilder.paginate(userService.getAllUsers(pageRequest));
    }

    @GetMapping("/{id}")
    public UserSpecial getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public void createUser(@Body CreateUserDto user) {
        userService.createUser(user);
    }
}
