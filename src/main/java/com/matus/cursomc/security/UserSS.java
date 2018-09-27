package com.matus.cursomc.security;

import com.matus.cursomc.domain.enums.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSS implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authority;

    public UserSS(){

    }

    public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        // Converte lista de Set Perfil para GrandAuthority
        // x ==> Para cada Perfil 'x' na lista de Perfil
        // Vai ser dado um new SimpleGrantedAuthority()
        // SimpleGrantedAuthority() ==> Recebe o "ROLE_CLIENTE" ou "ROLE_ADMIN" da classe Enum, e faz uma lista. É padrão do Spring Security receber o ROLE_NOME
        this.authority = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toSet());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Fala se a conta esta expirada
        // Pode implementar toda a logica aqui
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

    public boolean hasRole(Perfil perfil) {
        //Testa se um usuario possui o perfil passado;
        return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
    }
}
