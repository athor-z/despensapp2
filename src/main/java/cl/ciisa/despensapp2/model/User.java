package cl.ciisa.despensapp2.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6281526699139867510L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String username;

    @Column(name = "restriction")
    @Enumerated(EnumType.STRING)
    private FoodRestriction restriction;
    
    @OneToOne(mappedBy = "user")
    private Pantry pantry;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
 /*
    @Override
    public String getUsername() {
        return this.email;
    }
*/ 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
}

