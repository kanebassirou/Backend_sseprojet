package com.sseprojet.sseprojet.security;

import com.sseprojet.sseprojet.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {
    private Integer id;
    private String nom;
    private String email;
    private String motDePasseHash;
    private String role;

    public UserPrincipal(Integer id, String nom, String email, String motDePasseHash, String role) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasseHash = motDePasseHash;
        this.role = role;
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
            user.getId(),
            user.getNom(),
            user.getEmail(),
            user.getMotDePasseHash(),
            user.getRole()
        );
    }

    // Getters
    public Integer getId() { 
        return id; 
    }
    
    public String getNom() { 
        return nom; 
    }
    
    public String getRole() { 
        return role; 
    }

    public String getEmail() { 
        return email; 
    }

    @Override
    public String getUsername() { 
        return email; 
    }

    @Override
    public String getPassword() { 
        return motDePasseHash; 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

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
