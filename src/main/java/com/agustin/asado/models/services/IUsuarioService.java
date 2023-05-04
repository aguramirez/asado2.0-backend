package com.agustin.asado.models.services;

import com.agustin.asado.models.entity.Usuario;

import java.util.List;

public interface IUsuarioService {

    public List<Usuario> findAll();

    public Usuario findById(Long id);

    public Usuario save(Usuario usuario);

    public void delete(Long id);
}
