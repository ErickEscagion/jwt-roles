package eoen.jwtroles.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @Id
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;

}
