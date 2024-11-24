package com.khodecamp.online.shop.modules.user;

import com.khodecamp.online.shop.core.exception.BadRequestException;
import com.khodecamp.online.shop.core.exception.InternalServerErrorException;
import com.khodecamp.online.shop.core.exception.NotFoundException;
import com.khodecamp.online.shop.modules.user.dto.CreateUserDto;
import com.khodecamp.online.shop.modules.user.dto.UserSpecial;
import com.khodecamp.online.shop.modules.user.mapper.UserMapper;
import com.khodecamp.online.shop.modules.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserByUsername(String username) {
        User user = userMapper.selectByUsername(username);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }

    public UserSpecial getUserById(Long id) {
        UserSpecial user = userMapper.selectUserById(id);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }

    public void createUser(CreateUserDto user) {
        try {
            // check if user already exists
            User userExists = userMapper.selectByUsername(user.getUsername());
            if (userExists != null) {
                throw new BadRequestException("User already exists");
            }

            userMapper.insertUser(user);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to create user");
        }
    }
}
