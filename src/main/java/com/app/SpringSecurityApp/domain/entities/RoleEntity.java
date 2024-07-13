package com.app.SpringSecurityApp.domain.entities;

import com.app.SpringSecurityApp.domain.models.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "role-permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name="permission_id"))
    private Set<PermissionEntity> permissions = new HashSet<>();
}
