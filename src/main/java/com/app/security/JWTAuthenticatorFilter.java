package com.app.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.interfaces.IUserDetails;
import com.app.modelos.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticatorFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		Usuario authCredentials = new Usuario();

		try {
			authCredentials = new ObjectMapper().readValue(request.getReader(), Usuario.class);
		} catch (IOException e) {

		}

		UsernamePasswordAuthenticationToken usernamePat = new UsernamePasswordAuthenticationToken(
				authCredentials.getUsuario(), authCredentials.getPassword(), Collections.emptyList());

		return getAuthenticationManager().authenticate(usernamePat);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		IUserDetails userdataili = (IUserDetails) authResult.getPrincipal();

		String token = TokenUtils.crearToken("gestor", userdataili.getUsername());

		PrintWriter out = response.getWriter();
		response.setContentType("aplication/json");
		response.setStatus(HttpStatus.CREATED.value());
		response.getWriter().flush();

		token tok = new token();

		tok.setAcces_token("Authorization");
		tok.setToken_type("Bearer");
		tok.setToken(token);
		tok.setTime_expire(new Date(System.currentTimeMillis() + (10800L*1000)));
		tok.setTimeAt(new Date(System.currentTimeMillis()));
		Gson gson = new GsonBuilder().serializeNulls().create();
		String employeeJsonString = gson.toJson(tok);

		out.print(employeeJsonString);
		out.flush();
		// response.addHeader("Authorization", "Bearer " + token);
		// TODO Auto-generated method stub
		super.successfulAuthentication(request, response, chain, authResult);
	}

}
