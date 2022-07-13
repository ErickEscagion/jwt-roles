package eoen.jwtroles.mappers;

import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eoen.jwtroles.dtos.UserRequestDTO;
import eoen.jwtroles.dtos.UserResponseDTO;
import eoen.jwtroles.entities.Role;
import eoen.jwtroles.entities.User;

public class UserMapper {
    
	
    public static UserResponseDTO fromUserToResponse(User user) {
		return new UserResponseDTO(user.getUserName(), user.getUserFirstName(), user.getUserLastName(), RoleMapper.fromRoleToResponse(user.getRole()));
	}

	public static User fromDtoToUser(UserRequestDTO dto, Set<Role> roles) {	
		return new User(null,dto.getUserName(), dto.getUserFirstName(), dto.getUserLastName(), new BCryptPasswordEncoder().encode(dto.getUserPassword()),roles,true);
	}

}
