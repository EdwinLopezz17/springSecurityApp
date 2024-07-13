package com.app.SpringSecurityApp.config.service;

import com.app.SpringSecurityApp.app.dto.request.LoginRequest;
import com.app.SpringSecurityApp.app.dto.response.AuthResponse;
import com.app.SpringSecurityApp.config.util.JwtUtil;
import com.app.SpringSecurityApp.domain.entities.UserEntity;
import com.app.SpringSecurityApp.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public UserDetailServiceImpl(PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User "+username+" not found"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role ->authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRole())));

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

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateToken(authentication);

        return AuthResponse.builder()
                .token(token)
                .message("Longed in successfully")
                .success(true)
                .build();
    }


    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);

       if(userDetails == null){
           throw new UsernameNotFoundException("User "+username+" not found");
       }

       if(!passwordEncoder.matches(password, userDetails.getPassword())){
           throw new BadCredentialsException("Bad credentials");
       }
       return new UsernamePasswordAuthenticationToken(
               userDetails.getUsername(),
               userDetails.getPassword(),
               userDetails.getAuthorities());

    }
}
