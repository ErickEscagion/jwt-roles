package eoen.jwtroles.mappers;

import eoen.jwtroles.dtos.RoleRequestDTO;
import eoen.jwtroles.dtos.RoleResponseDTO;
import eoen.jwtroles.entities.Role;

public class RoleMapper {
    
    public static RoleResponseDTO fromRoleToResponse(Role role) {
		return new RoleResponseDTO(role.getRoleName(), role.getRoleDescription());
	}

	public static Role fromDtoToRole(RoleRequestDTO dto) {	
		return new Role(null, dto.getRoleName(), dto.getRoleDescription());
	}

}
