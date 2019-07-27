package ro.utcluj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcluj.model.Sold;

@Repository
public interface SoldRepository extends JpaRepository<Sold, Integer> {
}
