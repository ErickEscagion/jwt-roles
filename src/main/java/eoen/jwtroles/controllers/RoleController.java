package eoen.jwtroles.controllers;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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
import eoen.jwtroles.dtos.RoleRequestDTO;
import eoen.jwtroles.dtos.RoleResponseDTO;
import eoen.jwtroles.entities.Role;
import eoen.jwtroles.exception.EntityNotFoundException;
import eoen.jwtroles.mappers.RoleMapper;
import eoen.jwtroles.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping("v1/role")
@PreAuthorize("hasRole('Admin')")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Operation(summary = "Post Role", description = "Route to post a new Role", tags = "Role")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Role created!"),
			@ApiResponse(responseCode = "400", description = "Argument not valid!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
	})
	@PostMapping
	public ResponseEntity<RoleResponseDTO> postNewRole(@Valid @RequestBody RoleRequestDTO dto) {
		Role roleSaved = roleService.postNewRole(RoleMapper.fromDtoToRole(dto));
		return ResponseEntity.ok(RoleMapper.fromRoleToResponse(roleSaved));
	}

	@Operation(summary = "Get Role", description = "Route to get all Roles", tags = "Role")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get roles success!", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "500", description = "Parameter from pageable invalid!", content = @Content(schema = @Schema(hidden = true)))
	})
	@GetMapping
	public ResponseEntity<Page<RoleResponseDTO>> getAllRoles(@ParameterObject @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(roleService.getAllRoles(pageable).map(RoleMapper::fromRoleToResponse));
	}

	@Operation(summary = "Get Role By Id", description = "Route to get Role By Id", tags = "Role")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get role by id success!"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "Role not found!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class)))
	})
	@GetMapping("{id}")
	public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id) {
		Role role = roleService.getRoleById(id);
		return ResponseEntity.ok(RoleMapper.fromRoleToResponse(role));
	}

	@Operation(summary = "Delete Role", description = "Route to delete Role", tags = "Role")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Role deleted!", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "400", description = "Role in use!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "Role not found!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class)))
	})
	@DeleteMapping("{id}")
	public ResponseEntity<RoleResponseDTO> deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Update Role", description = "Route to update Role", tags = "Role")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Role update!"),
			@ApiResponse(responseCode = "400", description = "Argument not valid!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "404", description = "Role not found! or RoleName in use!", content = @Content(schema = @Schema(implementation = ErrorsDTO.class)))
	})
	@PutMapping("{id}")
	public ResponseEntity<RoleResponseDTO> updateRole(@Valid @RequestBody RoleRequestDTO dto,
			@PathVariable Long id) {
		try {
			Role role = roleService.updateRole(RoleMapper.fromDtoToRole(dto), id);
			return ResponseEntity.ok(RoleMapper.fromRoleToResponse(role));
		} catch (RuntimeException ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

}
