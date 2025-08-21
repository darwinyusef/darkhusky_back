package com.darkhusky.darkhusky_back.service;

import com.darkhusky.darkhusky_back.entity.UsuarioEntity;
import com.darkhusky.darkhusky_back.entity.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Locale.filter;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioEntity registro (UsuarioEntity usuario){
        usuario.setIsActive(true);
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public Optional<UsuarioEntity> login (String email, String password){
        return usuarioRepository.findByEmail(email).filter(u -> u.getPasswordHash().equals(password));
    }


}
