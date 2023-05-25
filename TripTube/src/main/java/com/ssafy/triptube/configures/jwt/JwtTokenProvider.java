package com.ssafy.triptube.configures.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.triptube.configures.jwt.dtos.TokenDto;
import com.ssafy.triptube.configures.redis.RedisService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

	private final UserDetailsService userDetailsService;

	private final RedisService redisService;

	private static final String EMAIL_KEY = "email";

	private String secretKey = "SSAFY9thLeeDongKyuAndKimChangHyeokTripTubeSSAFY9thLeeDongKyuAndKimChangHyeokTripTube";

	private static Key signingKey;

	private final Long accessTokenValidTime = 30 * 60 * 1000L; // 30 minutes

	private final Long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L; // 1 week

	@Override
	public void afterPropertiesSet() throws Exception {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());

		byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
		signingKey = Keys.hmacShaKeyFor(secretKeyBytes);
	}

	@Transactional
	public TokenDto createToken(String email) {
		Long now = System.currentTimeMillis();

		String accessToken = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS512")
				.setExpiration(new Date(now + accessTokenValidTime)).setSubject("access-token").claim(EMAIL_KEY, email)
				.signWith(signingKey, SignatureAlgorithm.HS512).compact();

		String refreshToken = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS512")
				.setExpiration(new Date(now + refreshTokenValidTime)).setSubject("refresh-token")
				.signWith(signingKey, SignatureAlgorithm.HS512).compact();

		return new TokenDto(accessToken, refreshToken);
	}

	public Claims getClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) { // Access Token
			return e.getClaims();
		}
	}

	public Authentication getAuthentication(String token) {
		String email = getClaims(token).get(EMAIL_KEY).toString();

		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUserEmail(String token) {
		return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody().getSubject();
	}

	public long getTokenExpirationTime(String token) {
		return getClaims(token).getExpiration().getTime();
	}

	public boolean validateRefreshToken(String refreshToken) {
		try {
			if (redisService.getValues(refreshToken).equals("delete")) { // 회원 탈퇴했을 경우
				return false;
			}
			Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(refreshToken);
			return true;
		} catch (SignatureException e) {
			System.out.println("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			System.out.println("Invalid JWT token.");
		} catch (ExpiredJwtException e) {
			System.out.println("Expired JWT token.");
		} catch (UnsupportedJwtException e) {
			System.out.println("Unsupported JWT token.");
		} catch (IllegalArgumentException e) {
			System.out.println("JWT claims string is empty.");
		} catch (NullPointerException e) {
			System.out.println("JWT Token is empty.");
		}
		return false;
	}

	public boolean validateAccessToken(String accessToken) {
		try {
			if (redisService.getValues(accessToken) != null // NPE 방지
					&& redisService.getValues(accessToken).equals("logout")) { // 로그아웃 했을 경우
				return false;
			}
			Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(accessToken);
			return true;
		} catch (ExpiredJwtException e) {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validateAccessTokenOnlyExpired(String accessToken) {
		try {
			return getClaims(accessToken).getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
