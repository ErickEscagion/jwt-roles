package eoen.jwtroles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eoen.jwtroles.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
