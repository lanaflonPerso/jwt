package co.simplon.filrouge.repository;

import co.simplon.filrouge.modele.FingerPrint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FingerPrintRepository extends JpaRepository<FingerPrint, Long>,JpaSpecificationExecutor<FingerPrint> {}
