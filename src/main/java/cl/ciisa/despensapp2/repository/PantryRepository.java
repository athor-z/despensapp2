package cl.ciisa.despensapp2.repository;

import cl.ciisa.despensapp2.model.Pantry;
import cl.ciisa.despensapp2.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PantryRepository extends JpaRepository<Pantry, Long> {

	//Pantry findByUsername(String username);
	Pantry findByUser (User user);

}