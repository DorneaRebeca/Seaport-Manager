package ro.utcluj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcluj.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
}
