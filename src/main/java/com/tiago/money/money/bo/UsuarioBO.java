package com.tiago.money.money.bo;

import com.tiago.money.money.model.Usuario;
import com.tiago.money.money.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioBO {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> buscarComBasePermissao(String permissao) {
        return this.usuarioRepository.findByPermissaoDescricao(permissao);
    }

}
