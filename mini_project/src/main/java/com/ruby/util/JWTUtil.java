package com.ruby.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

public class JWTUtil {

	public static final long ACCESS_TOKEN_MSEC = 24*60*60*1000;
	public static final String JWT_KEY = "key";
	public static final String prefix = "Bearer ";
	public static final String usernameClaim = "username";	
	public static final String aliasClaim = "alias";	
	public static final String providerClaim = "provider";
	public static final String emailClaim = "email";
	
	private static String getJWTSource(String token) {
		if(token.startsWith(prefix)) return token.replace(prefix, "");
		return token;
	}
	
	public static String getJWT(String username) {
		String src = JWT.create()
				.withClaim(usernameClaim, username)
				.withExpiresAt(new Date(System.currentTimeMillis()+ACCESS_TOKEN_MSEC))
				.sign(Algorithm.HMAC256(JWT_KEY));
		return prefix+src;
	}
	
	public static String getJWT(String username, String provider, String email) {
		String src = JWT.create()
				.withClaim(usernameClaim, username)
				.withClaim(providerClaim, provider)
				.withClaim(emailClaim, email)
				.withExpiresAt(new Date(System.currentTimeMillis()+ACCESS_TOKEN_MSEC))
				.sign(Algorithm.HMAC256(JWT_KEY));
		return prefix+src;
	}
	
	public static boolean isExpired(String token) {
		String tok = getJWTSource(token);
		return JWT.require(Algorithm.HMAC256(JWT_KEY)).build()
				.verify(tok).getExpiresAt().before(new Date());
	}
	
	public static String getClaim(String token, String cname) {
		String tok = getJWTSource(token);
		Claim claim = JWT.require(Algorithm.HMAC256(JWT_KEY)).build()
				.verify(tok).getClaim(cname);
		if(claim.isMissing()) return null;
		return claim.asString();
	}
}
