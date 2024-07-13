package com.app.SpringSecurityApp.config.service;

import com.app.SpringSecurityApp.domain.entities.UserEntity;
import com.app.SpringSecurityApp.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User "+username+" not found"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role ->authorities.add(new SimpleGrantedAuthority("ROLE_"+role)));

        userEntity.getRoles().stream()
                .flatMap(role->role.getPermissions().stream())
                .forEach(permission->authorities.add(new SimpleGrantedAuthority(permission.getPermission().name())));

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEnabled(),
                userEntity.getAccountNonExpired(),
                userEntity.getCredentialsNonExpired(),
                userEntity.getAccountNonLocked(),
                authorities
        );
    }
}
