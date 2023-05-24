package com.ssafy.triptube.users.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.support.web.ApiResponseUtil;
import com.ssafy.triptube.users.dtos.LoginInfoDto;
import com.ssafy.triptube.users.dtos.LoginRequestDto;
import com.ssafy.triptube.users.dtos.RegistRequestDto;
import com.ssafy.triptube.users.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/users")
@RequiredArgsConstructor
public class UserPublicController {

	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
		LoginInfoDto loginInfoDto = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

		if (loginInfoDto == null) {
			return ApiResponseUtil.createResponse(false, "로그인 실패");
		}

		return ApiResponseUtil.createResponse(true, "로그인 성공", loginInfoDto);
	}

	@PostMapping("/register")
	public ResponseEntity<?> regist(@RequestBody RegistRequestDto registRequestDto) {
		LoginInfoDto loginInfoDto = userService.regist(registRequestDto.getEmail(), registRequestDto.getPassword(),
				registRequestDto.getName());

		if (loginInfoDto == null) {
			return ApiResponseUtil.createResponse(false, "회원가입 실패");
		}

		return ApiResponseUtil.createResponse(true, "회원가입 성공", loginInfoDto);
	}

	@GetMapping(value = "/profiles/{filename:.+}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> getProfileImage(@PathVariable String filename) throws IOException {
		InputStream image = userService.getProfileImage(filename);

		return ResponseEntity.ok().body(IOUtils.toByteArray(image));
	}

}
