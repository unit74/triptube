package com.ssafy.triptube.users.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;
import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.triptube.configures.jwt.dtos.TokenDto;
import com.ssafy.triptube.support.web.ApiResponseDto;
import com.ssafy.triptube.users.dtos.UpdateNameDto;
import com.ssafy.triptube.users.dtos.UpdatePasswordDto;
import com.ssafy.triptube.users.dtos.UserDto;
import com.ssafy.triptube.users.services.AuthService;
import com.ssafy.triptube.users.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/private/users")
@RequiredArgsConstructor
public class UserPrivateController {

	private final AuthService authService;

	private final UserService userService;

	private final long COOKIE_EXPIRATION = 90 * 24 * 60 * 60; // 90일

	@PostMapping("/profiles")
	public ResponseEntity<?> updateProfileImage(@RequestParam("image") MultipartFile multipartFile) {
		userService.updateProfileImage(getLoginUserId(), multipartFile);

		return createResponse(true, "프로필 이미지 업로드");
	}

	@PutMapping("/name")
	public ResponseEntity<?> updateUserName(@RequestBody UpdateNameDto updateNameDto) {
		return createResponse(true, "유저 이름 업데이트",
				userService.updateUserName(getLoginUserId(), updateNameDto.getName()));
	}

	@PutMapping("/password")
	public ResponseEntity<?> updateUserPassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
		UserDto userDto = userService.updateUserPassword(getLoginUserId(), updatePasswordDto.getCurrentPassword(),
				updatePasswordDto.getNewPassword());

		if (userDto == null) {
			return createResponse(false, "기존 비밀번호가 맞지 않음");
		}

		return createResponse(true, "유저 비밀번호 업데이트", userDto);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String requestAccessToken) {
		authService.logout(requestAccessToken);
		ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "").maxAge(0).path("/").build();

		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, responseCookie.toString())
				.body(new ApiResponseDto<>(true, "로그아웃"));
	}

	@PostMapping("/validate")
	public ResponseEntity<?> validate(@RequestHeader("Authorization") String requestAccessToken) {
		if (!authService.validate(requestAccessToken)) {
			return ResponseEntity.status(HttpStatus.OK).build(); // 재발급 필요X
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 재발급 필요
		}
	}

	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(@CookieValue(name = "refresh-token") String requestRefreshToken,
			@RequestHeader("Authorization") String requestAccessToken) {
		TokenDto reissuedTokenDto = authService.reissue(requestAccessToken, requestRefreshToken);

		if (reissuedTokenDto != null) {
			ResponseCookie responseCookie = ResponseCookie.from("refresh-token", reissuedTokenDto.getRefreshToken())
					.maxAge(COOKIE_EXPIRATION).build();
			return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, responseCookie.toString())
					.body(new ApiResponseDto<>(true, "재발급", "Bearer " + reissuedTokenDto.getAccessToken()));

		} else {
			ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "").maxAge(0).path("/").build();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.header(HttpHeaders.SET_COOKIE, responseCookie.toString()).build();
		}
	}

}
