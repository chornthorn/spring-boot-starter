package com.khodecamp.online.shop.modules.user;

import com.khodecamp.online.shop.core.exception.BadRequestException;
import com.khodecamp.online.shop.core.exception.InternalServerErrorException;
import com.khodecamp.online.shop.core.exception.NotFoundException;
import com.khodecamp.online.shop.core.pagination.PaginationManager;
import com.khodecamp.online.shop.core.pagination.PaginationResponse;
import com.khodecamp.online.shop.core.request.PaginationRequest;
import com.khodecamp.online.shop.modules.user.dto.CreateUserDto;
import com.khodecamp.online.shop.modules.user.dto.UserSpecial;
import com.khodecamp.online.shop.modules.user.mapper.UserMapper;
import com.khodecamp.online.shop.modules.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final PaginationManager paginationManager;

    public UserService(UserMapper userMapper, PaginationManager paginationManager) {
        this.userMapper = userMapper;
        this.paginationManager = paginationManager;
    }

    public PaginationResponse<User> getAllUsers(PaginationRequest paginationRequest) {
        return paginationManager.paginate(
                new PaginationManager.Pager(paginationRequest.getPage(), paginationRequest.getLimit()),
                req -> userMapper.selectAll(req.getOffset(), req.getLimit()),
                (r) -> userMapper.countAll()
        );
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
