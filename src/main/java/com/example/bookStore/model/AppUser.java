package com.example.bookStore.model;

import com.example.bookStore.jwt.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
@Entity
@Getter
@Setter
@Builder
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;

    private String password;
    private Date createdAt;
    private Date updateAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    public AppUser() {
    }

    public AppUser(UUID id, String name, String email, String password, Date createdAt, Date updateAt, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser appUser)) return false;
        return Objects.equals(getId(), appUser.getId()) && Objects.equals(getName(), appUser.getName()) && Objects.equals(getEmail(), appUser.getEmail()) && Objects.equals(getPassword(), appUser.getPassword()) && Objects.equals(getCreatedAt(), appUser.getCreatedAt()) && Objects.equals(getUpdateAt(), appUser.getUpdateAt()) && getRole() == appUser.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getPassword(), getCreatedAt(), getUpdateAt(), getRole());
    }
}
