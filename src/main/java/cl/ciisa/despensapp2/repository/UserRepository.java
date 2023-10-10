package cl.ciisa.despensapp2.repository;
import java.util.List;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import cl.ciisa.despensapp2.model.FoodRestriction;
import cl.ciisa.despensapp2.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	//Optional<User> findByUsername(String username);
	UserDetails findByUsername(String username);
	List<User> findByRestriction(FoodRestriction restriction);
	User findUserByUsername(String username);
	User findByEmail(String email);
}