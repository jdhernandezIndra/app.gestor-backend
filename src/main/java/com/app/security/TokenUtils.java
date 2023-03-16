package com.app.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.app.interfaces.IUserDetails;
import com.app.modelos.Usuario;
import com.app.servicios.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenUtils {
	private final static String ACCES_TOKEN_SECRET = "ASD563ASK455NknlkANSkhJ1LKSNaASknask35nKAN45S456";
	private final static Long ACCES_TOKEN_VALIDITY_SECONDS = 10800L;

	private static Logger logger = LoggerFactory.getLogger(TokenUtils.class);

	public static String crearToken(IUserDetails userdataili, String userName, String userPasword) {
		Long tiempoExpiracion = ACCES_TOKEN_VALIDITY_SECONDS * 1000;
		Date fechaExpiracion = new Date(System.currentTimeMillis() + tiempoExpiracion);

		Map<String, Object> extra = new HashMap<>();
		extra.put("roles", userdataili.getAuthorities());

		return Jwts.builder().setSubject(userPasword).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(fechaExpiracion).addClaims(extra)
				.signWith(Keys.hmacShaKeyFor(ACCES_TOKEN_SECRET.getBytes())).compact();
	}

	public static UsernamePasswordAuthenticationToken getAuhentication(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(ACCES_TOKEN_SECRET.getBytes()).build()
					.parseClaimsJws(token).getBody();
			String pasword = claims.getSubject();

			logger.info(claims.get("roles").toString());
			//Collection<SimpleGrantedAuthority>authoritie = new ObjectMapper().readValue(claims.get("roles").toString(), SimpleGrantedAuthority.class);
			final Collection<SimpleGrantedAuthority> authorities =
					Arrays.stream(claims.get("roles").toString().split(","))
							.map(SimpleGrantedAuthority::new)
							.collect(Collectors.toList());
			
			logger.info(authorities.toString());
			return new UsernamePasswordAuthenticationToken(pasword, null, authorities);
		} catch (JwtException e) {
			return null;
		}
	}

}
