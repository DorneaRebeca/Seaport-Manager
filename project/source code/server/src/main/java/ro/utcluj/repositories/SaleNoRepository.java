package ro.utcluj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcluj.model.Salenumber;

@Repository
public interface SaleNoRepository extends JpaRepository<Salenumber, Integer> {
}
