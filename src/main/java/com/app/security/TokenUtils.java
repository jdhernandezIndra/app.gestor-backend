package com.app.security;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenUtils {
	private final static String ACCES_TOKEN_SECRET = "ASD563ASK455NknlkANSkhJ1LKSNaASknask35nKAN45S456";
	private final static Long ACCES_TOKEN_VALIDITY_SECONDS = 36000L;

	public static String crearToken(String userName, String userPasword) {
		Long tiempoExpiracion = ACCES_TOKEN_VALIDITY_SECONDS * 1_000;
		Date fechaExpiracion = new Date(System.currentTimeMillis() + tiempoExpiracion);

		Map<String, Object> extra = new HashMap<>();

		return Jwts.builder().setSubject(userPasword).setExpiration(fechaExpiracion).addClaims(extra)
				.signWith(Keys.hmacShaKeyFor(ACCES_TOKEN_SECRET.getBytes())).compact();
	}

	public static UsernamePasswordAuthenticationToken getAuhentication(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(ACCES_TOKEN_SECRET.getBytes()).build()
					.parseClaimsJws(token).getBody();
			String pasword = claims.getSubject();

			return new UsernamePasswordAuthenticationToken(pasword, null, Collections.emptyList());
		} catch (JwtException e) {
			return null;
		}
	}

}
