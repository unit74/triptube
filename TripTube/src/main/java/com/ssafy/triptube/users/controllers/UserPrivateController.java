package com.ssafy.triptube.users.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;
import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.triptube.users.dtos.RegistRequestDto;
import com.ssafy.triptube.users.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/private/users")
@RequiredArgsConstructor
public class UserPrivateController {

	private final UserService userService;

	@PostMapping("/profiles")
	public ResponseEntity<?> updateProfileImage(@RequestParam("image") MultipartFile multipartFile) {
		userService.updateProfileImage(getLoginUserId(), multipartFile);

		return createResponse(true, "프로필 이미지 업로드");
	}

	@PutMapping("")
	public ResponseEntity<?> updateUser(@RequestBody RegistRequestDto registRequestDto) {
		return createResponse(true, "유저 정보 업데이트", userService.updateUser(getLoginUserId(), registRequestDto.getEmail(),
				registRequestDto.getPassword(), registRequestDto.getName()));
	}

}
