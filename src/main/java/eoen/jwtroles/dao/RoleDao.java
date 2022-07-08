package eoen.jwtroles.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eoen.jwtroles.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {

}
