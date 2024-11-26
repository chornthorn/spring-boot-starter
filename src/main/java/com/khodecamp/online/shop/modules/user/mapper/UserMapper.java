package com.khodecamp.online.shop.modules.user.mapper;

import com.khodecamp.online.shop.modules.auth.model.Role;
import com.khodecamp.online.shop.modules.auth.model.ResourcePermission;
import com.khodecamp.online.shop.modules.user.dto.CreateUserDto;
import com.khodecamp.online.shop.modules.user.dto.UpdateUserDto;
import com.khodecamp.online.shop.modules.user.dto.UserSpecial;
import com.khodecamp.online.shop.modules.user.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    @Select("SELECT r.* FROM ROLES r " +
            "JOIN USER_ROLES ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    Set<Role> getUserRoles(Long userId);

    @Select("SELECT DISTINCT res.name as resource, s.name as scope " +
            "FROM USERS u " +
            "JOIN USER_ROLES ur ON u.id = ur.user_id " +
            "JOIN ROLES r ON ur.role_id = r.id " +
            "JOIN ROLES_PERMISSIONS rp ON r.id = rp.role_id " +
            "JOIN PERMISSIONS p ON rp.permission_id = p.id " +
            "JOIN RESOURCES res ON p.resource_id = res.id " +
            "JOIN SCOPES s ON p.scope_id = s.id " +
            "WHERE u.id = #{userId}")
    Set<ResourcePermission> getUserPermissions(Long userId);

    @Select("SELECT EXISTS(" +
            "SELECT 1 FROM USERS u " +
            "JOIN USER_ROLES ur ON u.id = ur.user_id " +
            "JOIN ROLES r ON ur.role_id = r.id " +
            "JOIN ROLES_PERMISSIONS rp ON r.id = rp.role_id " +
            "JOIN PERMISSIONS p ON rp.permission_id = p.id " +
            "JOIN RESOURCES res ON p.resource_id = res.id " +
            "JOIN SCOPES s ON p.scope_id = s.id " +
            "WHERE u.id = #{userId} " +
            "AND res.name = #{resource} " +
            "AND s.name = #{scope})")
    boolean hasPermission(@Param("userId") Long userId,
                          @Param("resource") String resource,
                          @Param("scope") String scope);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM USERS WHERE ID = #{id}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "roles", column = "ID",
                    javaType = Set.class,
                    many = @Many(select = "getUserRoles"))
    })
    User findById(Long id);

    @Insert("INSERT INTO USER_ROLES (user_id, role_id) VALUES (#{userId}, #{roleId})")
    void assignRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Delete("DELETE FROM USER_ROLES WHERE user_id = #{userId} AND role_id = #{roleId}")
    void removeRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Insert("INSERT INTO USERS (username, email, password, created_at, updated_at) " +
            "VALUES (#{username}, #{email}, #{password}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "username", column = "username"),
            @Result(property = "roles", column = "ID",
                    javaType = Set.class,
                    many = @Many(select = "getUserRoles"))
    })
    User findByUsernameWithRoles(String username);

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

        public String queryUserPermissions(){
            return new SQL() {{
                SELECT_DISTINCT("res.name as resource, s.name as scope");
                FROM("USERS u");
                JOIN("USER_ROLES ur ON u.id = ur.user_id");
                JOIN("ROLES r ON ur.role_id = r.id");
                JOIN("ROLES_PERMISSIONS rp ON r.id = rp.role_id");
                JOIN("PERMISSIONS p ON rp.permission_id = p.id");
                JOIN("RESOURCES res ON p.resource_id = res.id");
                JOIN("SCOPES s ON p.scope_id = s.id");
                WHERE("u.id = #{userId}");
            }}.toString();
        }
    }
}
