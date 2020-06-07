package fr.utbm.lo54projet.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.utbm.lo54projet.Entity.Client;

public interface ClientDao extends JpaRepository<Client, Long>{
	
	List<Client> findAll();

	Optional<Client> findById(Long clientId);

}