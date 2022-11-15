package ru.promo.springapp.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.Collections;

@JsonIncludeProperties(value = {"id", "login", "email", "fio", "role", "company"})
@Getter
@Setter
@Entity
@Table(name = "profiles")
public class Profile implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String login;
    private String password;
    private String email;
    private String fio;

    @Enumerated(value = EnumType.STRING)
    private ProfileRole role;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return login;
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
