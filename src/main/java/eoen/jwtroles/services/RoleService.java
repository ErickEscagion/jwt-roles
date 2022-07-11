package eoen.jwtroles.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eoen.jwtroles.entities.Role;
import eoen.jwtroles.exception.BdException;
import eoen.jwtroles.repositories.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role postNewRole(Role role) {
        Optional<Role> roleActive = roleRepository.findByRolename(role.getRoleName());
		if (roleActive.isPresent())
			throw new BdException("roleName already exists without bd!");
        return roleRepository.save(role);
    }
}
