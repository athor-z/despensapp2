package cl.ciisa.despensapp2.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import cl.ciisa.despensapp2.jwt.JwtTokenFilter;
import cl.ciisa.despensapp2.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
 
@Configuration
public class ApplicationSecurity {
	
	@Autowired private JwtTokenFilter jwtTokenFilter;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests()
        .requestMatchers("/auth/login","/users","/css/**").permitAll()
        .anyRequest().authenticated();
        http.exceptionHandling()
        	.authenticationEntryPoint(
        			(request, response, ex)->{
        				response.sendError(
        						HttpServletResponse.SC_UNAUTHORIZED,
        						ex.getMessage()
        						);
        			}
        			
        			);
        
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    @Autowired
    private UserRepository userRepo;
 
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepo.findByEmail(username);
                //.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));
    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /*
    //@Override
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
        return auth.build();
    }
	*/
}
