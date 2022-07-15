package eoen.jwtroles.controllers;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eoen.jwtroles.dtos.ErrorsDTO;
import eoen.jwtroles.dtos.UserRequestDTO;
import eoen.jwtroles.dtos.UserResponseDTO;
import eoen.jwtroles.entities.Role;
import eoen.jwtroles.entities.User;
import eoen.jwtroles.exception.BdException;
import eoen.jwtroles.exception.EntityNotFoundException;
import eoen.jwtroles.mappers.UserMapper;
import eoen.jwtroles.services.InitializeDataService;
import eoen.jwtroles.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequestMapping("v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private InitializeDataService initializeDataService;

	@PostConstruct
	public void initRoleAndUser() {
		initializeDataService.initRoleAndUser();
	}

	@Operation(summary = "Post new User", description = "Route to post a new User", tags = "User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User created!"),
			@ApiResponse(responseCode = "400", description = "Argument not valid!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class))),
	})
	@PostMapping({ "/postUser" })
	public ResponseEntity<UserResponseDTO> postNewUser(@Valid @RequestBody UserRequestDTO dto) {
		User userSaved = userService.postNewUser(UserMapper.fromDtoToUser(dto, null));
		return ResponseEntity.ok(UserMapper.fromUserToResponse(userSaved));
	}

	@Operation(summary = "Get Users", description = "Route to get all Users", tags = "User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get all users success!", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "500", description = "Parameter from pageable invalid!", content = @Content(schema = @Schema(hidden = true)))
	})
	@GetMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<UserResponseDTO>> getUsers(@ParameterObject @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(userService.getUsers(pageable).map(UserMapper::fromUserToResponse));
	}

	@Operation(summary = "Get Users Active", description = "Route to get all Users Active", tags = "User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get all users active success!", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "500", description = "Parameter from pageable invalid!", content = @Content(schema = @Schema(hidden = true)))
	})
	@GetMapping({ "/active" })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<UserResponseDTO>> getActiveUsers(@ParameterObject @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(userService.getActiveUsers(pageable).map(UserMapper::fromUserToResponse));
	}

	@Operation(summary = "Get Users Disabled", description = "Route to get all Users Disabled", tags = "User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get all users disabled success!", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "500", description = "Parameter from pageable invalid!", content = @Content(schema = @Schema(hidden = true)))
	})
	@GetMapping({ "/disabled" })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<UserResponseDTO>> getDisabledUsers(@ParameterObject @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(userService.getDisabledUsers(pageable).map(UserMapper::fromUserToResponse));
	}

	@Operation(summary = "Get User By Id", description = "Route to get User By Id", tags = "User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get user by id success!"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "User not found!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class)))
	})
	@GetMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return ResponseEntity.ok(UserMapper.fromUserToResponse(user));
	}

	@Operation(summary = "Update User", description = "Route to update User", tags = "User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User update!"),
			@ApiResponse(responseCode = "400", description = "Argument not valid!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "User not found! or UserName in use!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class)))
	})
	@PutMapping("{id}")
	@PreAuthorize("hasRole('User')")
	public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserRequestDTO dto,
			@PathVariable Long id, String password) {
		try {
			Set<Role> roles = userService.getUserById(id).getRole();
			User user = userService.updateUser(UserMapper.fromDtoToUser(dto, roles), id, password);
			return ResponseEntity.ok(UserMapper.fromUserToResponse(user));
		} catch (RuntimeException ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	@Operation(summary = "Update Role to Admin", description = "Route to update User role to Admin", tags = "User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User update!"),
			@ApiResponse(responseCode = "400", description = "Argument not valid!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
	})
	@PutMapping({ "/toAdmin/{id}" })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponseDTO> updateRoleToAdmin(@PathVariable Long id) {
		try {
			User user = userService.updateRoleToAdmin(id);
			return ResponseEntity.ok(UserMapper.fromUserToResponse(user));
		} catch (RuntimeException ex) {
			throw new BdException(ex.getMessage());
		}
	}

	@Operation(summary = "Disable User", description = "Route to disable User", tags = "User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User Disabled!", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "User not found!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class)))
	})
	@DeleteMapping("{id}")
	public ResponseEntity<UserResponseDTO> disabledUser(@PathVariable Long id) {
		userService.disabledUser(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Active User", description = "Route to active User", tags = "User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User Activate!", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "User not found!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class)))
	})
	@DeleteMapping("/active/{id}")
	public ResponseEntity<UserResponseDTO> activeUser(@PathVariable Long id) {
		userService.activeUser(id);
		return ResponseEntity.ok().build();
	}
}
