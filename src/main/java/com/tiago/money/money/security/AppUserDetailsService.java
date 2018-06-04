package com.tiago.money.money.security;

import com.tiago.money.money.model.Usuario;
import com.tiago.money.money.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> optinalUsuario = this.usuarioRepository.findByEmail(email);
        Usuario usuario = optinalUsuario.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha inválido."));
        return new User(email, usuario.getSenha(), this.getPermissoes(usuario));
    }

    private Set<SimpleGrantedAuthority> getPermissoes(Usuario usuario) {
        Set<SimpleGrantedAuthority> permissoesUsuario = new HashSet<>();
        usuario.getPermissao().forEach(permissao -> {
            permissoesUsuario.add(new SimpleGrantedAuthority(permissao.getDescricao().toUpperCase()));
        });

        return permissoesUsuario;
    }
}
