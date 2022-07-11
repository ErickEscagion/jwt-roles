package eoen.jwtroles.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import eoen.jwtroles.entities.Role;
import eoen.jwtroles.exception.BdException;
import eoen.jwtroles.exception.EntityNotFoundException;
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

    public Page<Role> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Role getRoleById(Long id) {
        Optional<Role> optional = roleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Role Not Found!");
        }
        return optional.get();
    }

    public void deleteRole(Long id) {
        Role baseRole = this.getRoleById(id);
        // logica para validar se a role esta em uso
        roleRepository.delete(baseRole);
    }

    public Role updateRole(Role role, Long id) {

        System.out.println("----------------------");
        // logica para validar se a role esta em uso

        Role baseRole = this.getRoleById(id);
        role.setRoleId(baseRole.getRoleId());

        Optional<Role> roleActive = roleRepository.findByRolename(role.getRoleName());
        if (roleActive.isPresent() && baseRole.getRoleName() != roleActive.get().getRoleName())
            throw new BdException("roleName already exists without bd!");
        return roleRepository.save(role);
    }
}
