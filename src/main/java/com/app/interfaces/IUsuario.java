package com.app.interfaces;

import java.util.List;

import com.app.modelos.Usuario;

public interface IUsuario {
	public List<Usuario> listar();

	public Usuario usuarioPorUser(String user);

	public Usuario guardar(Usuario U);

	public Usuario actualiza(Usuario u);

	public Usuario inhabilitar(Usuario u);
}
