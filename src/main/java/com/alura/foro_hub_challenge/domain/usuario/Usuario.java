package com.alura.foro_hub_challenge.domain.usuario;

import com.alura.foro_hub_challenge.domain.perfil.Perfil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "correo_electronico", unique = true) // Mapeo exacto a la DB
    private String correoElectronico;

    private String contrasena;

    // Relación ManyToMany con Perfiles (Roles)
    // Usamos EAGER para cargar los permisos al momento del login
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_perfiles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private List<Perfil> perfiles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convierte los perfiles (Entidad) a Autoridades (Spring Security)
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return contrasena; // Spring usa esto para comparar el Hash
    }

    @Override
    public String getUsername() {
        return correoElectronico; // Usamos el correo como "usuario" para el login
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