package com.app.repositorios;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.modelos.Usuario;

@Repository
public interface IUsuarios extends CrudRepository<Usuario, Long> {
	Optional<Usuario> findOneByUsuario(String usuario);
}
