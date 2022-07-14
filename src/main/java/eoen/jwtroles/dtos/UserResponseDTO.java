package eoen.jwtroles.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    @Schema(example = "UserName")
    private String userName;

    @Schema(example = "UserFirstName")
    private String userFirstName;

    @Schema(example = "UserLastName")
    private String userLastName;

    private Set<RoleResponseDTO> role;
}
