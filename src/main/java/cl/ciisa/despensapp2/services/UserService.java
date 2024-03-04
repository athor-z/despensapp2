package cl.ciisa.despensapp2.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.ciisa.despensapp2.model.FoodRestriction;
import cl.ciisa.despensapp2.model.User;
import cl.ciisa.despensapp2.repository.UserRepository;

@Service
@Transactional
public class UserService{
	@Autowired
    private UserRepository userRepository;
//    @Autowired
//    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // constructor and other methods omitted for brevity
    
/*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByUsername(username).get();
      if (user == null) {
        throw new UsernameNotFoundException("Invalid username or password.");
      }
      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }
    */
    @PostMapping
    public User createUser(@RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        //return userService.createUser(user);
        return userRepository.save(user);
    }    
    
    public boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
    
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
    //NUEVO
    public String findEmailByUsername(String userName) {
        return userRepository.findUserByUsername(userName).getEmail();
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findUsersByRestriction(FoodRestriction restriction) {
        return userRepository.findByRestriction(restriction);
    }
    
    //04-03-24
    public Long findUserIdByUsername(String username) {
        return userRepository.findUserIdByUsername(username);
    }

}