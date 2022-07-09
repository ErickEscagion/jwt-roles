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
public class UserRequestDTO {

    @Column(unique = true)
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;

}
