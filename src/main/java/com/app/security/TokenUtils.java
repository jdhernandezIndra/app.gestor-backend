package com.app.security;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.app.interfaces.IUserDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenUtils {
	private final static String ACCES_TOKEN_SECRET = "ASD563ASK455NknlkANSkhJ1LKSNaASknask35nKAN45S456";
	private final static Long ACCES_TOKEN_VALIDITY_SECONDS = 10800L;

	//private static Logger logger = LoggerFactory.getLogger(TokenUtils.class);

	public static String crearToken(IUserDetails userdataili, String userName, String userPasword) {
		Long tiempoExpiracion = ACCES_TOKEN_VALIDITY_SECONDS * 1000;
		Date fechaExpiracion = new Date(System.currentTimeMillis() + tiempoExpiracion);

		Map<String, Object> extra = new HashMap<>();
		extra.put("authorities", userdataili.getAuthorities());

		return Jwts.builder().setSubject(userPasword).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(fechaExpiracion).addClaims(extra)
				.signWith(Keys.hmacShaKeyFor(ACCES_TOKEN_SECRET.getBytes())).compact();
	}

	public static UsernamePasswordAuthenticationToken getAuhentication(String token) throws IOException {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(ACCES_TOKEN_SECRET.getBytes()).build()
					.parseClaimsJws(token).getBody();
			String pasword = claims.getSubject();
			String auth = claims.get("authorities").toString();
			Set<GrantedAuthority> autorithies = new HashSet<>();
			Type tipoEmpleados = new TypeToken<List<rolSecurity>>() {
			}.getType();
			List<rolSecurity> roles = new Gson().fromJson(auth, tipoEmpleados);

			for (rolSecurity rol : roles) {
				autorithies.add(new SimpleGrantedAuthority(rol.getAuthority()));
			}
			return new UsernamePasswordAuthenticationToken(pasword, null, autorithies);
		} catch (JwtException e) {
			return null;
		}
	}

}
