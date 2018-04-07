package co.simplon.filrouge.repository;

import co.simplon.filrouge.modele.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long>, JpaSpecificationExecutor<People> {}
