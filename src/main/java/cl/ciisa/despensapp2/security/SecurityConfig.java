package cl.ciisa.despensapp2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	/* .csrf().disable().authorizeHttpRequests() */

	@Bean
	public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().authorizeHttpRequests()
				.requestMatchers("/login","/users/register/**","/resources/**", "/css/**", "/assets/**", "/dist/**", "/src/**").permitAll()
				.anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/")
				.failureUrl("/login?error").permitAll().and().logout().logoutUrl("/logout").permitAll();

		return http.build();
	}

	/*
	 * //@SuppressWarnings("deprecation") protected void configure(HttpSecurity
	 * http) throws Exception { http .csrf()
	 * .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) .and()
	 * .authorizeHttpRequests() .requestMatchers("/login","/logout",
	 * "/resources/**", "/css/**").permitAll()
	 * //.requestMatchers("/updatePantryName").authenticated()
	 * .anyRequest().authenticated() .and() .formLogin() .loginPage("/login")
	 * .defaultSuccessUrl("/") .failureUrl("/login?error") .permitAll() .and()
	 * .logout() .logoutUrl("/logout") .permitAll(); }
	 */

	/*
	 * public void configure(WebSecurity web) {
	 * web.ignoring().requestMatchers("/error"); }
	 */
}