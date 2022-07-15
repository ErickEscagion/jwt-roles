package eoen.jwtroles.services;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import eoen.jwtroles.entities.Role;
import eoen.jwtroles.entities.User;
import eoen.jwtroles.repositories.RoleRepository;
import eoen.jwtroles.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;
@Service
public class InitializeDataService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void initRoleAndUser() {
        initRoles();
        initUsers();
        setRolesToUsers();
    }

    private void initRoles(){
        Role adminRole = new Role();
        adminRole.setRoleId(1l);
        adminRole.setRoleName("ADMIN");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleId(2l);
        userRole.setRoleName("USER");
        userRole.setRoleDescription("User role (Default)");
        roleRepository.save(userRole);
    }

    private void initUsers(){
        User adminUser = new User();
        adminUser.setUserId(1l);
        adminUser.setUserName("admin");
        adminUser.setUserPassword(getEncodedPassword("senha123"));
        adminUser.setUserFirstName("adminFistName");
        adminUser.setUserLastName("adminLastName");
        adminUser.setActive(true);
        userRepository.save(adminUser);

        User user = new User();
        user.setUserId(2l);
        user.setUserName("user");
        user.setUserPassword(getEncodedPassword("senha123"));
        user.setUserFirstName("userFistName");
        user.setUserLastName("userFistName");
        user.setActive(true);
        userRepository.save(user);
    }

    private void setRolesToUsers(){
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleRepository.findById(1l).get());
        adminRoles.add(roleRepository.findById(2l).get());
        
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.findById(2l).get());

        User adminUser = userRepository.findById(1l).get();
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);
        
        User userUser = userRepository.findById(2l).get();
        userUser.setRole(userRoles);
        userRepository.save(userUser);
    }
}
