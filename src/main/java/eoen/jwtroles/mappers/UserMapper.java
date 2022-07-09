package eoen.jwtroles.mappers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import eoen.jwtroles.dtos.UserRequestDTO;
import eoen.jwtroles.dtos.UserResponseDTO;
import eoen.jwtroles.entities.User;

public class UserMapper {
    
	
    public static UserResponseDTO fromUserToResponse(User user) {
		return new UserResponseDTO(user.getUserName(), user.getUserFirstName(), user.getUserLastName(), user.getRole());
	}

	public static User fromDtoToUser(UserRequestDTO dto) {	
		return new User(null,dto.getUserName(), dto.getUserFirstName(), dto.getUserLastName(), new BCryptPasswordEncoder().encode(dto.getUserPassword()),null);
	}

}
