package eoen.jwtroles.dtos;

import eoen.jwtroles.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponseDTO {

    private User user;
    private String jwtToken;

}
