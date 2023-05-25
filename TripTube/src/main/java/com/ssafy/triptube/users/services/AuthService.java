package com.ssafy.triptube.users.services;

import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.triptube.configures.jwt.JwtTokenProvider;
import com.ssafy.triptube.configures.jwt.dtos.TokenDto;
import com.ssafy.triptube.configures.redis.RedisService;
import com.ssafy.triptube.users.dtos.LoginRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final RedisService redisService;

	private final String SERVER = "Server";

	@Transactional
	public TokenDto login(LoginRequestDto loginDto) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getEmail(), loginDto.getPassword());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return generateToken(SERVER, authentication.getName());
	}

	public boolean validate(String requestAccessTokenInHeader) {
		String requestAccessToken = resolveToken(requestAccessTokenInHeader);
		return jwtTokenProvider.validateAccessTokenOnlyExpired(requestAccessToken);
	}

	@Transactional
	public TokenDto reissue(String requestAccessTokenInHeader, String requestRefreshToken) {
		String requestAccessToken = resolveToken(requestAccessTokenInHeader);

		Authentication authentication = jwtTokenProvider.getAuthentication(requestAccessToken);
		String principal = getPrincipal(requestAccessToken);

		String refreshTokenInRedis = redisService.getValues("RT(" + SERVER + "):" + principal);
		if (refreshTokenInRedis == null) {
			return null;
		}

		if (!jwtTokenProvider.validateRefreshToken(requestRefreshToken)
				|| !refreshTokenInRedis.equals(requestRefreshToken)) {
			redisService.deleteValues("RT(" + SERVER + "):" + principal);
			return null;
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		redisService.deleteValues("RT(" + SERVER + "):" + principal);
		TokenDto tokenDto = jwtTokenProvider.createToken(principal);
		saveRefreshToken(SERVER, principal, tokenDto.getRefreshToken());
		return tokenDto;
	}

	@Transactional
	public TokenDto generateToken(String provider, String email) {
		if (redisService.getValues("RT(" + provider + "):" + email) != null) {
			redisService.deleteValues("RT(" + provider + "):" + email);
		}

		TokenDto tokenDto = jwtTokenProvider.createToken(email);
		saveRefreshToken(provider, email, tokenDto.getRefreshToken());
		return tokenDto;
	}

	@Transactional
	public void saveRefreshToken(String provider, String principal, String refreshToken) {
		redisService.setValuesWithTimeout("RT(" + provider + "):" + principal, refreshToken,
				jwtTokenProvider.getTokenExpirationTime(refreshToken));
	}

	public String getPrincipal(String requestAccessToken) {
		return jwtTokenProvider.getAuthentication(requestAccessToken).getName();
	}

	public String resolveToken(String requestAccessTokenInHeader) {
		if (requestAccessTokenInHeader != null && requestAccessTokenInHeader.startsWith("Bearer ")) {
			return requestAccessTokenInHeader.substring(7);
		}
		return null;
	}

	@Transactional
	public void logout(String requestAccessTokenInHeader) {
		String requestAccessToken = resolveToken(requestAccessTokenInHeader);
		String principal = getPrincipal(requestAccessToken);

		String refreshTokenInRedis = redisService.getValues("RT(" + SERVER + "):" + principal);
		if (refreshTokenInRedis != null) {
			redisService.deleteValues("RT(" + SERVER + "):" + principal);
		}

		long expiration = jwtTokenProvider.getTokenExpirationTime(requestAccessToken) - new Date().getTime();
		redisService.setValuesWithTimeout(requestAccessToken, "logout", expiration);
	}
}