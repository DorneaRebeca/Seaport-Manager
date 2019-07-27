package ro.utcluj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ro.utcluj.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
}
