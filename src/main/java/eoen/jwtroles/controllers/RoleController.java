package eoen.jwtroles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eoen.jwtroles.dtos.RoleRequestDTO;
import eoen.jwtroles.dtos.RoleResponseDTO;
import eoen.jwtroles.entities.Role;
import eoen.jwtroles.mappers.RoleMapper;
import eoen.jwtroles.services.RoleService;

@RestController
@RequestMapping("v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping()
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<RoleResponseDTO> postNewRole(@RequestBody RoleRequestDTO dto) {
        Role roleSaved = roleService.postNewRole(RoleMapper.fromDtoToRole(dto));
		return ResponseEntity.ok(RoleMapper.fromRoleToResponse(roleSaved));
    }

    
}
