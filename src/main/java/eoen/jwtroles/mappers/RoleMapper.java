package eoen.jwtroles.mappers;

import java.util.HashSet;
import java.util.Set;

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

    public static Set<RoleResponseDTO> fromRoleToResponse(Set<Role> roles) {
		Set<RoleResponseDTO> rolesResponse = new HashSet<>();
        for (Role role : roles) {
			rolesResponse.add(fromRoleToResponse(role));
		}
		return rolesResponse;
    }

}
