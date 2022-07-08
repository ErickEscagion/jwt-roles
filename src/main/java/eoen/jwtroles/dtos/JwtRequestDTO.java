package eoen.jwtroles.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequestDTO {

    private String userName;
    private String userPassword;

}
