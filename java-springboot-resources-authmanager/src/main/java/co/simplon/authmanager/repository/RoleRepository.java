package co.simplon.authmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.simplon.authmanager.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
