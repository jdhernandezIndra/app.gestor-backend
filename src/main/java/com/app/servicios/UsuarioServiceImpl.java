package com.app.servicios;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub

		return (List<Usuario>) UsuariosData.findAll();
	}

	@Override
	public Usuario usuarioPorUser(String user) {
		// TODO Auto-generated method stub
		Usuario u = UsuariosData.findOneByUsuario(user).orElse(null);
		u.setPassword("");
		return u;
	}

	@Override
	public Usuario guardar(Usuario u) {
		// TODO Auto-generated method stub
		u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
		return UsuariosData.save(u);
	}

	@Override
	public Usuario actualiza(Usuario us) {
		// TODO Auto-generated method stub
		Usuario u = UsuariosData.findOneByUsuario(us.getUsuario()).orElse(null);
		if (u != null) {
			us.setId(u.getId());
			us.setPassword(new BCryptPasswordEncoder().encode(us.getPassword()));
			UsuariosData.save(us);
		}
		us.setPassword("");
		return us;
	}

	@Override
	public Usuario inhabilitar(Usuario u) {
		Usuario user = UsuariosData.findOneByUsuario(u.getUsuario()).orElse(null);

		if (user != null) {
			user.setEstado(false);
			;
		}
		return UsuariosData.save(u);
	}

	@Override
	public Usuario cambioRol(Usuario u) {
		Usuario user = UsuariosData.findOneByUsuario(u.getUsuario()).orElse(null);

		if (user != null) {
			user.setRol(u.getRol());
		}
		return UsuariosData.save(user);
	}

}
