package eoen.jwtroles.controllers;

import javax.validation.Valid;

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

import eoen.jwtroles.dtos.RoleRequestDTO;
import eoen.jwtroles.dtos.RoleResponseDTO;
import eoen.jwtroles.entities.Role;
import eoen.jwtroles.exception.EntityNotFoundException;
import eoen.jwtroles.mappers.RoleMapper;
import eoen.jwtroles.services.RoleService;

@RestController
@RequestMapping("v1/role")
@PreAuthorize("hasRole('Admin')")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping
	public ResponseEntity<RoleResponseDTO> postNewRole(@Valid @RequestBody RoleRequestDTO dto) {
		Role roleSaved = roleService.postNewRole(RoleMapper.fromDtoToRole(dto));
		return ResponseEntity.ok(RoleMapper.fromRoleToResponse(roleSaved));
	}

	@GetMapping
	public ResponseEntity<Page<RoleResponseDTO>> getAllRoles(@PageableDefault Pageable pageable) {
		return ResponseEntity.ok(roleService.getAllRoles(pageable).map(RoleMapper::fromRoleToResponse));
	}

	@GetMapping("{id}")
	public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id) {
		Role role = roleService.getRoleById(id);
		return ResponseEntity.ok(RoleMapper.fromRoleToResponse(role));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<RoleResponseDTO> deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
		return ResponseEntity.ok().build();
	}

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
