package com.app.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.interfaces.IUserDetails;
import com.app.modelos.Usuario;
import com.app.repositorios.IUsuarios;

@Service
public class IUserDetailServices implements UserDetailsService {

	@Autowired
	private IUsuarios iUserConsumo;

	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Usuario user = iUserConsumo.findOneByUsuario(usuario)
				.orElseThrow(() -> new UsernameNotFoundException("EL USUARIO NO EXISTE"));

		return new IUserDetails(user);
	}

}
