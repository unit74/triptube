package com.ssafy.triptube.users.controllers;

import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.configures.jwt.dtos.TokenDto;
import com.ssafy.triptube.support.web.ApiResponseDto;
import com.ssafy.triptube.users.dtos.LoginInfoDto;
import com.ssafy.triptube.users.dtos.LoginRequestDto;
import com.ssafy.triptube.users.dtos.RegistRequestDto;
import com.ssafy.triptube.users.services.AuthService;
import com.ssafy.triptube.users.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/users")
@RequiredArgsConstructor
public class UserPublicController {

	private final AuthService authService;

	private final UserService userService;

	private final long COOKIE_EXPIRATION = 90 * 24 * 60 * 60; // 90일

	@PostMapping("/register")
	public ResponseEntity<?> signup(@RequestBody RegistRequestDto registRequestDto) {
		boolean result = userService.regist(registRequestDto.getEmail(), registRequestDto.getPassword(),
				registRequestDto.getName());

		if (!result) {
			return createResponse(false, "중복된 이메일");
		}

		return login(new LoginRequestDto(registRequestDto.getEmail(), registRequestDto.getPassword()));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto) {
		TokenDto tokenDto = authService.login(loginDto);

		HttpCookie httpCookie = ResponseCookie.from("refresh-token", tokenDto.getRefreshToken())
				.maxAge(COOKIE_EXPIRATION).build();

		LoginInfoDto loginInfoDto = userService.me(loginDto.getEmail());
		loginInfoDto.setToken("Bearer " + tokenDto.getAccessToken());

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, httpCookie.toString())
				.body(new ApiResponseDto<>(true, "로그인", loginInfoDto));
	}

}
