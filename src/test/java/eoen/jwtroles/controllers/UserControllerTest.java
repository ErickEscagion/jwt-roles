package eoen.jwtroles.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import eoen.jwtroles.dtos.UserRequestDTO;
import eoen.jwtroles.entities.Role;
import eoen.jwtroles.entities.User;
import eoen.jwtroles.mappers.UserMapper;
import eoen.jwtroles.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    private User user;
    private UserRequestDTO userRequestDTO;

    @BeforeEach
    public void setup() {
        Set<Role> roles = new HashSet<>();
        userRequestDTO = new UserRequestDTO("userNameUserUnitTest", "userFirstNameUserUnitTest", "userLastNameUserUnitTest", "senha123", roles);
        user = UserMapper.fromDtoToUser(userRequestDTO,roles);

        List<User> users = new ArrayList<>();
        Page<User> usersList = new PageImpl<>(users);

        when(userService.getUsers(any(Pageable.class))).thenReturn(usersList);
        when(userService.getActiveUsers(any(Pageable.class))).thenReturn(usersList);
        when(userService.getDisabledUsers(any(Pageable.class))).thenReturn(usersList);
        when(userService.getUserById(123l)).thenReturn(user);
        when(userService.updateRoleToAdmin(123l)).thenReturn(user);
    }

    @Test
    @WithMockUser
    public void postNewUserTest() throws Exception {
        //IMPLEMENTAR
    }


    @Test
    @WithMockUser(roles = {"USER"})
    public void updateUserTest() throws Exception {
        //IMPLEMENTAR
    }

    @Test
    public void UserGetUsersTest() throws Exception {
        mockMvc.perform(get("/v1/user"))
                .andExpect(status().is(401));
    }

    @Test
    public void UserGetActiveUsersTest() throws Exception {
        mockMvc.perform(get("/v1/user/active"))
                .andExpect(status().is(401));
    }

    @Test
    public void UserGetDisabledUsersTest() throws Exception {
        mockMvc.perform(get("/v1/user/disabled"))
                .andExpect(status().is(401));
    }

    @Test
    public void UserGetUserByIdTest() throws Exception {
        mockMvc.perform(get("/v1/user/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    public void UserUpdateRoleToAdminTest() throws Exception {
        mockMvc.perform(put("/v1/user/toAdmin/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().is(401));
    }

    @Test
    public void UserDisabledUserTest() throws Exception {
        mockMvc.perform(delete("/v1/user/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    public void UserActiveUserTest() throws Exception {
        mockMvc.perform(delete("/v1/user/active/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void AdminGetUsersTest() throws Exception {
        mockMvc.perform(get("/v1/user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void AdminGetActiveUsersTest() throws Exception {
        mockMvc.perform(get("/v1/user/active"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void AdminGetDisabledUsersTest() throws Exception {
        mockMvc.perform(get("/v1/user/disabled"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void AdminGetUserByIdTest() throws Exception {
        mockMvc.perform(get("/v1/user/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void AdminUpdateRoleToAdminTest() throws Exception {
        mockMvc.perform(put("/v1/user/toAdmin/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void AdminDisabledUserTest() throws Exception {
        mockMvc.perform(delete("/v1/user/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "ADMIN" })
    public void AdminActiveUserTest() throws Exception {
        mockMvc.perform(delete("/v1/user/active/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
}
