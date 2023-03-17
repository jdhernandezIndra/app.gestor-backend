package com.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.app.modelos.Usuario;
import com.app.servicios.UsuarioServiceImpl;

@RestController
public class ControlServices {
	@Autowired
	private UsuarioServiceImpl UsuarioServices;

	@GetMapping("ping")
	public String ping() {
		return "Pong!";
	}


	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("token/habilita/{user}")
	public Usuario TokenVerify(@PathVariable String user) {

		return UsuarioServices.usuarioPorUser(user);
	}
}
