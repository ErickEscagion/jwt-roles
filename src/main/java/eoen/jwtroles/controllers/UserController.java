package eoen.jwtroles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eoen.jwtroles.dtos.UserRequestDTO;
import eoen.jwtroles.dtos.UserResponseDTO;
import eoen.jwtroles.entities.Role;
import eoen.jwtroles.entities.User;
import eoen.jwtroles.exception.EntityNotFoundException;
import eoen.jwtroles.mappers.UserMapper;
import eoen.jwtroles.services.UserService;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

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
    public ResponseEntity<UserResponseDTO> postNewUser(@Valid @RequestBody UserRequestDTO dto) {
        User userSaved = userService.postNewUser(UserMapper.fromDtoToUser(dto,null));
		return ResponseEntity.ok(UserMapper.fromUserToResponse(userSaved));
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@PageableDefault Pageable pageable) {
		return ResponseEntity.ok(userService.getAllUsers(pageable).map(UserMapper::fromUserToResponse));
	}

	@GetMapping("{id}")
    @PreAuthorize("hasRole('Admin')")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return ResponseEntity.ok(UserMapper.fromUserToResponse(user));
	}

	@PutMapping("{id}")
    @PreAuthorize("hasRole('User')")
	public ResponseEntity<UserResponseDTO> updateRole(@Valid @RequestBody UserRequestDTO dto,
			@PathVariable Long id, String password) {
		try {
			Set<Role> roles = userService.getUserById(id).getRole();
			User user = userService.updateUser(UserMapper.fromDtoToUser(dto,roles), id,password);
			return ResponseEntity.ok(UserMapper.fromUserToResponse(user));
		} catch (RuntimeException ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}
}

