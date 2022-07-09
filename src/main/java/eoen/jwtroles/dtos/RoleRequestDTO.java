package eoen.jwtroles.dtos;

import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDTO {
    
    @Column(unique = true)
    private String roleName;
    private String roleDescription;
    
}
