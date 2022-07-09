package eoen.jwtroles.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponseDTO {

    private UserResponseDTO user;
    private String jwtToken;

}
