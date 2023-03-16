package com.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.modelos.Usuario;
import com.app.servicios.UsuarioServiceImpl;

@RestController
@RequestMapping("usuarios/")
public class UsuariosController {

	@Autowired
	private UsuarioServiceImpl UsuarioServices;

	//@Secured({"ROLE_ADMIN"})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("lista")
	public List<Usuario> listar() {
		return UsuarioServices.listar();
	}

	@GetMapping("us/{user}")
	public Usuario usuarioId(@PathVariable String user) {
		return UsuarioServices.usuarioPorUser(user);
	}

	@PostMapping("guardar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario guardar(@RequestBody Usuario u) {
		return UsuarioServices.guardar(u);
	}

	@PutMapping("actualiza")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario actualiza(@RequestBody Usuario u) {

		return UsuarioServices.actualiza(u);
	}

	@PutMapping("inhabilitar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario inhabilitar(@RequestBody Usuario u) {
		return UsuarioServices.inhabilitar(u);
	}

}
