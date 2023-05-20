package com.ssafy.triptube.users.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.triptube.configures.jwt.JwtTokenProvider;
import com.ssafy.triptube.users.dtos.LoginInfoDto;
import com.ssafy.triptube.users.models.UserEntity;
import com.ssafy.triptube.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtTokenProvider jwtTokenProvider;

	public LoginInfoDto login(String email, String password) {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null) {
			return null;
		}

		if (!passwordEncoder.matches(password, userEntity.getPassword())) {
			return null;
		}

		LoginInfoDto loginInfoDto = new LoginInfoDto();

		loginInfoDto.setToken(jwtTokenProvider.createToken(userEntity.getEmail()));
		loginInfoDto.setName(userEntity.getName());
		loginInfoDto.setProfilePhotoUrl(userEntity.getProfilePhotoUrl());

		return loginInfoDto;
	}

	public LoginInfoDto regist(String email, String password, String name) {
		if (userRepository.existsByEmail(email)) {
			return null;
		}

		UserEntity userEntity = new UserEntity();

		userEntity.setEmail(email);
		userEntity.setPassword(password);
		userEntity.encodePassword(passwordEncoder);
		userEntity.setName(name);

		userEntity = userRepository.save(userEntity);

		LoginInfoDto loginInfoDto = new LoginInfoDto();

		loginInfoDto.setToken(jwtTokenProvider.createToken(userEntity.getEmail()));
		loginInfoDto.setName(userEntity.getName());
		loginInfoDto.setProfilePhotoUrl(userEntity.getProfilePhotoUrl());

		return loginInfoDto;
	}
}
