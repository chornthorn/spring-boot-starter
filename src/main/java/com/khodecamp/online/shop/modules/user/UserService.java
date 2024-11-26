package com.khodecamp.online.shop.modules.user;

import com.khodecamp.online.shop.core.exception.BadRequestException;
import com.khodecamp.online.shop.core.exception.InternalServerErrorException;
import com.khodecamp.online.shop.core.exception.NotFoundException;
import com.khodecamp.online.shop.core.pagination.PaginationManager;
import com.khodecamp.online.shop.core.pagination.PaginationResponse;
import com.khodecamp.online.shop.core.request.PaginationRequest;
import com.khodecamp.online.shop.modules.auth.dto.UserRegistrationDto;
import com.khodecamp.online.shop.modules.user.dto.CreateUserDto;
import com.khodecamp.online.shop.modules.user.mapper.UserMapper;
import com.khodecamp.online.shop.modules.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PaginationManager paginationManager;
    private final PasswordEncoder passwordEncoder;

    public User loadUserById(Long id) {
        User user = userMapper.findById(id);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }


    // findByUsernameWithRoles
    public User findByUsernameWithRoles(String username) {
        User user = userMapper.findByUsernameWithRoles(username);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }


    @Transactional
    public User createUser(UserRegistrationDto registrationDto) {
        validateNewUser(registrationDto);

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        userMapper.insert(user);

        // Assign default role
        assignDefaultRole(user.getId());

        return user;
    }

    private void validateNewUser(UserRegistrationDto dto) {
        if (userMapper.findByUsername(dto.getUsername()) != null) {
            throw new BadRequestException("Username already exists");
        }
    }

    private void assignDefaultRole(Long userId) {
//        userMapper.assignRole(userId, 1L);
    }

    public PaginationResponse<User> getAllUsers(PaginationRequest paginationRequest) {
        return paginationManager.paginate(
                new PaginationManager.Pager(paginationRequest.getPage(), paginationRequest.getLimit()),
                req -> userMapper.selectAll(req.getOffset(), req.getLimit()),
                (r) -> userMapper.countAll()
        );
    }

    public User getUserByUsername(String username) {
        User user = userMapper.findByUsernameWithRoles(username);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }

    public User getUserById(Long id) {
        User user = userMapper.findById(id);

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
