package eoen.jwtroles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eoen.jwtroles.dtos.UserRequestDTO;
import eoen.jwtroles.dtos.UserResponseDTO;
import eoen.jwtroles.entities.User;
import eoen.jwtroles.mappers.UserMapper;
import eoen.jwtroles.services.UserService;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/postUser"})
    public ResponseEntity<UserResponseDTO> postNewUser(@RequestBody UserRequestDTO dto) {
        User userSaved = userService.postNewUser(UserMapper.fromDtoToUser(dto));
		return ResponseEntity.ok(UserMapper.fromUserToResponse(userSaved));
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }
}
