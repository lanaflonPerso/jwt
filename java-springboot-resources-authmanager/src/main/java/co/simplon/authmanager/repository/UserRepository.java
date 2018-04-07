package co.simplon.authmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.simplon.authmanager.model.User;
import java.lang.String;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findByUsername(String username);
	
	List<User> findByEmail(String email);
}
