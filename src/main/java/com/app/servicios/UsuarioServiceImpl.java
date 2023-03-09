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
	public Usuario actualiza(String user, Usuario us) {
		// TODO Auto-generated method stub
		Usuario u = UsuariosData.findOneByUsuario(user).orElse(null);
		if (u != null) {
			us.setId(u.getId());
			us.setPassword(new BCryptPasswordEncoder().encode(us.getPassword()));
			us.setUrlImagen("");
			UsuariosData.save(us);
		}
		us.setPassword("");
		return us;
	}

	@Override
	public Usuario inhabilitar(Usuario u) {
		// TODO Auto-generated method stub
		return UsuariosData.save(u);
	}

}
