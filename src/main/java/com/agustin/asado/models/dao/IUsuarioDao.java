package com.agustin.asado.models.dao;

import com.agustin.asado.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

}
