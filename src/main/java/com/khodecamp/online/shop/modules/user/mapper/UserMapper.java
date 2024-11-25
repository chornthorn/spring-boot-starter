package com.khodecamp.online.shop.modules.user.mapper;

import com.khodecamp.online.shop.modules.user.dto.CreateUserDto;
import com.khodecamp.online.shop.modules.user.dto.UpdateUserDto;
import com.khodecamp.online.shop.modules.user.dto.UserSpecial;
import com.khodecamp.online.shop.modules.user.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface UserMapper {

    @SelectProvider(type = SqlProvider.class, method = "selectByUsername")
    User selectByUsername(String username);

    @SelectProvider(type = SqlProvider.class, method = "selectUserById")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "identity", column = "username"),
    })
    UserSpecial selectUserById(Long id);

    @InsertProvider(type = SqlProvider.class, method = "insertUser")
    int insertUser(CreateUserDto createUserDto);

    @UpdateProvider(type = SqlProvider.class, method = "updateUser")
    int updateUser(UpdateUserDto updateUserDto);

    @DeleteProvider(type = SqlProvider.class, method = "deleteUser")
    int deleteUser(Long id);

    @SelectProvider(type = SqlProvider.class, method = "selectAll")
    List<User> selectAll(int page, int limit);

    @SelectProvider(type = SqlProvider.class, method = "countAll")
    long countAll();

    class SqlProvider {

        private final String UserTable = "USERS";

        public String selectAll() {
            return new SQL() {{
                SELECT("*");
                FROM(UserTable);
                LIMIT("#{limit}");
                OFFSET("#{page}");
            }}.toString();
        }

        public String countAll() {
            return new SQL() {{
                SELECT("COUNT(*)");
                FROM(UserTable);
            }}.toString();
        }

        public String selectByUsername() {
            return new SQL() {{
                SELECT("*");
                FROM(UserTable);
                WHERE("username = #{username}");
            }}.toString();
        }

        public String selectUserById() {
            return new SQL() {{
                SELECT("*");
                FROM(UserTable);
                WHERE("id = #{id}");
            }}.toString();
        }

        public String insertUser() {
            // return "INSERT INTO USERS (username, password) VALUES (#{username}, #{password})";
            return new SQL() {{
                INSERT_INTO(UserTable);
                VALUES("username", "#{username}");
                VALUES("password", "#{password}");
                VALUES("email", "#{email}");
            }}.toString();
        }

        public String updateUser() {
            return new SQL() {{
                UPDATE(UserTable);
                SET("username = #{username}");
                SET("password = #{password}");
                WHERE("id = #{id}");
            }}.toString();
        }

        public String deleteUser() {
            return new SQL() {{
                DELETE_FROM(UserTable);
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}
