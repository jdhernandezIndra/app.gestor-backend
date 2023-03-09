package com.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@GetMapping("lista")
	public List<Usuario> listar() {
		return UsuarioServices.listar();
	}

	@GetMapping("{id}")
	public Usuario usuarioId(@PathVariable Long id) {
		return UsuarioServices.usuarioId(id);
	}

	@PostMapping("guardar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario guardar(@RequestBody Usuario u) {
		return UsuarioServices.guardar(u);
	}

	@PutMapping("actualiza/{user}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario actualiza(@PathVariable String user, @RequestBody Usuario u) {

		return UsuarioServices.actualiza(user, u);
	}

	@PutMapping("inhabilitar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario inhabilitar(@RequestBody Usuario u) {
		return UsuarioServices.inhabilitar(u);
	}

}
