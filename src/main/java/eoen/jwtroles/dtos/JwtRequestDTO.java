package eoen.jwtroles.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequestDTO {

    @Schema(example = "UserName")
    private String userName;

    @Schema(example = "Password")
    private String userPassword;

}
