package co.simplon.authmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.simplon.authmanager.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
	public List<Resource> findByIdIn(List<Long> listOfId); 
}
