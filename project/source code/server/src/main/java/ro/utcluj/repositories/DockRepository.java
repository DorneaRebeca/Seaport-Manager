package ro.utcluj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ro.utcluj.model.Dock;

@Repository
public interface DockRepository extends JpaRepository<Dock, Integer> {

}
