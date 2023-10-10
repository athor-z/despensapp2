package cl.ciisa.despensapp2.services;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import cl.ciisa.despensapp2.model.User;
import cl.ciisa.despensapp2.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	UserDetails user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        return user;
    }
}