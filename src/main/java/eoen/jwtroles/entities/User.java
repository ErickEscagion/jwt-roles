package eoen.jwtroles.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    @NotBlank(message = "userName must not be blank!")
    @NotNull(message = "userName must not be null!")
    @Size(min = 3, message = "userName size must be at least 3")
    private String userName;

    @NotBlank(message = "userFirstName must not be blank!")
    @NotNull(message = "userFirstName must not be null!")
    @Size(min = 2, message = "userFirstName size must be at least 2")
    private String userFirstName;

    @NotBlank(message = "userLastName must not be blank!")
    @NotNull(message = "userLastName must not be null!")
    @Size(min = 2, message = "userLastName size must be at least 2")
    private String userLastName;

    @NotBlank(message = "userPassword must not be blank!")
    @NotNull(message = "userPassword must not be null!")
    @Size(min = 5, message = "userPassword size must be at least 5")
    private String userPassword;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> role;

    private Boolean active;

}
