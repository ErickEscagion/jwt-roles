package eoen.jwtroles.dtos;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDTO {
    
    @Schema(example = "RoleName")
    @Column(unique = true)
    @NotBlank(message = "roleName must not be blank!")
    @NotNull(message = "roleName must not be null!")
    private String roleName;

    @Schema(example = "RoleDescription")
    @NotBlank(message = "roleDescription must not be blank!")
    @NotNull(message = "roleDescription must not be null!")
    @Size(min = 3, message = "roleDescription size must be at least 3")
    private String roleDescription;

    
}
