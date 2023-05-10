package cl.ciisa.despensapp2.model;

import jakarta.persistence.*;
//import org.springframework.security.core.GrantedAuthority;
@Entity
@Table(name = "users")
public class UserOLD {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String username;
/*
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    //private Role role;
*/
/*    
    @Column(nullable = false)
    private String restriction;
*/
    @Column(name = "restriction")
    @Enumerated(EnumType.STRING)
    private FoodRestriction restriction;
    // Constructors, getters, and setters

    public UserOLD() {}

    public UserOLD(String email, String password, String username, /*Role role,*/ FoodRestriction restriction) {
        this.email = email;
        this.password = password;
        this.username = username;
        //this.role = role;
        this.restriction = restriction;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
/*
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
*/
    public FoodRestriction getRestriction() {
        return restriction;
    }

    public void setRestriction(FoodRestriction restriction) {
        this.restriction = restriction;
    }
    
    // toString, equals, and hashCode
}