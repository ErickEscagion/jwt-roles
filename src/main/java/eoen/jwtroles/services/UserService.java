package eoen.jwtroles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import eoen.jwtroles.entities.Role;
import eoen.jwtroles.entities.User;
import eoen.jwtroles.repositories.RoleRepository;
import eoen.jwtroles.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleId(1l);
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleId(2l);
        userRole.setRoleName("User");
        userRole.setRoleDescription("User role (Default)");
        roleRepository.save(userRole);

        User adminUser = new User();
        adminUser.setUserId(1l);
        adminUser.setUserName("admin");
        adminUser.setUserPassword(getEncodedPassword("senha123"));
        adminUser.setUserFirstName("adminFistName");
        adminUser.setUserLastName("adminLastName");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);

        User user = new User();
        user.setUserId(2l);
        user.setUserName("user");
        user.setUserPassword(getEncodedPassword("senha123"));
        user.setUserFirstName("userFistName");
        user.setUserLastName("userFistName");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userRepository.save(user);
    }

    public User registerNewUser(User user) {

        Role role = roleRepository.findByRolename("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        
        User userSaved = userRepository.save(user);
        return userSaved;
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
