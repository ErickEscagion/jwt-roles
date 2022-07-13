package eoen.jwtroles.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponseDTO {

    private UserResponseDTO user;

    @Schema(example = "Valid Token")
    private String jwtToken;

}
