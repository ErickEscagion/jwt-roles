package eoen.jwtroles.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eoen.jwtroles.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String> {
}
