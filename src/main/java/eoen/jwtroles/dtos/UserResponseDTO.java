package eoen.jwtroles.dtos;

import eoen.jwtroles.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private String userName;
    private String userFirstName;
    private String userLastName;
    private Set<Role> role;
}
