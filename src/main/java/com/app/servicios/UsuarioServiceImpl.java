package com.app.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.interfaces.IUsuario;
import com.app.modelos.Usuario;
import com.app.repositorios.IUsuarios;

@Service
public class UsuarioServiceImpl implements IUsuario {

	@Autowired
	private IUsuarios UsuariosData;

	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub
		return (List<Usuario>) UsuariosData.findAll();
	}

	@Override
	public Usuario usuarioId(Long id) {
		// TODO Auto-generated method stub
		return UsuariosData.findById(id).orElse(null);
	}

	@Override
	public Usuario guardar(Usuario u) {
		// TODO Auto-generated method stub
		u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
		return UsuariosData.save(u);
	}

	@Override
	public Usuario actualiza(Usuario u) {
		// TODO Auto-generated method stub
		return UsuariosData.save(u);
	}

	@Override
	public Usuario inhabilitar(Usuario u) {
		// TODO Auto-generated method stub
		return UsuariosData.save(u);
	}

}
