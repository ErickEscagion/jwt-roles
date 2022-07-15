package eoen.jwtroles.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

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

import eoen.jwtroles.dtos.RoleRequestDTO;
import eoen.jwtroles.entities.Role;
import eoen.jwtroles.mappers.RoleMapper;
import eoen.jwtroles.services.RoleService;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private RoleService roleService;

    private Role role;
    private Role rolePut;
    private RoleRequestDTO roleRequestDTO;
    private RoleRequestDTO roleRequestDTOPut;

    @BeforeEach
    public void setup() {

        roleRequestDTO = new RoleRequestDTO("ROLENAMEUNITTEST", "role Description Unit Test");
        role = RoleMapper.fromDtoToRole(roleRequestDTO);

        roleRequestDTOPut = new RoleRequestDTO("ROLENAMEUNITTESTPUT", "role Description Unit Test Put");
        rolePut = RoleMapper.fromDtoToRole(roleRequestDTOPut);

        List<Role> roles = new ArrayList<>();
        Page<Role> rolesList = new PageImpl<>(roles);

        when(roleService.postNewRole(any(Role.class))).thenReturn(role);
        when(roleService.getAllRoles(any(Pageable.class))).thenReturn(rolesList);
        when(roleService.getRoleById(123l)).thenReturn(role);
        when(roleService.updateRole(any(Role.class), anyLong())).thenReturn(rolePut);
    }

    @Test
    public void userGetRolesTest() throws Exception {
        mockMvc.perform(get("/v1/role"))
                .andExpect(status().is(401));
    }

    @Test
    public void UserGetRoleByIdTest() throws Exception {
        mockMvc.perform(get("/v1/role/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    public void UserPostRoleTest() throws Exception {
        mockMvc.perform(post("/v1/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().is(401));

        mockMvc.perform(post("/v1/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(role)))
                .andExpect(status().is(401));
    }

    @Test
    public void UserDeleteRoleTest() throws Exception {
        mockMvc.perform(delete("/v1/role/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    public void UserUpdateRoleTest() throws Exception {
        mockMvc.perform(put("/v1/role/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().is(401));

        mockMvc.perform(put("/v1/role/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(role)))
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser(roles = { "Admin" })
    public void AdminGetRolesTest() throws Exception {
        mockMvc.perform(get("/v1/role"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "Admin" })
    public void AdminGetRoleByIdTest() throws Exception {
        mockMvc.perform(get("/v1/role/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "Admin" })
    public void AdminPostRoleTest() throws Exception {
        mockMvc.perform(post("/v1/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/v1/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(role)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "Admin" })
    public void AdminDeleteRoleTest() throws Exception {
        mockMvc.perform(delete("/v1/role/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = { "Admin" })
    public void AdminUpdateRoleTest() throws Exception {
        mockMvc.perform(put("/v1/role/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());

        mockMvc.perform(put("/v1/role/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(role)))
                .andExpect(status().isOk());
    }
}
