package ro.utcluj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcluj.model.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findByUsername(String username);
    void deleteById(int id);
}
