package eoen.jwtroles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eoen.jwtroles.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
