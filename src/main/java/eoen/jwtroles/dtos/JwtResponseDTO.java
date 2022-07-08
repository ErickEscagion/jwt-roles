package eoen.jwtroles.dtos;

import eoen.jwtroles.entities.User;

public class JwtResponseDTO {

    private User user;
    private String jwtToken;

    public JwtResponseDTO(User user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
