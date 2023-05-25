package com.ssafy.triptube.users.services;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.triptube.users.dtos.LoginInfoDto;
import com.ssafy.triptube.users.dtos.UserDto;
import com.ssafy.triptube.users.models.UserEntity;
import com.ssafy.triptube.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	@Value("${app.fileupload.uploadDir}")
	private String uploadFolder;

	@Value("${app.fileupload.uploadPath}")
	private String uploadPath;

	public boolean regist(String email, String password, String name) {
		if (userRepository.existsByEmail(email)) {
			return false;
		}

		UserEntity userEntity = new UserEntity();

		userEntity.setEmail(email);
		userEntity.setPassword(password);
		userEntity.encodePassword(passwordEncoder);
		userEntity.setName(name);

		userRepository.save(userEntity);

		return true;
	}

	@Transactional
	public UserDto updateProfileImage(Long userId, MultipartFile image) {
		UserEntity userEntity = userRepository.findById(userId).get();

		String imagePath = saveImage(userId, image);

		userEntity.setProfilePhotoUrl(imagePath);
		userEntity.setUpdatedAt(null);

		userEntity = userRepository.save(userEntity);

		UserDto userDto = new UserDto();

		userDto.setEmail(userEntity.getEmail());
		userDto.setName(userEntity.getName());
		userDto.setProfilePhotoUrl(userEntity.getProfilePhotoUrl());

		return userDto;
	}

	private String saveImage(Long userId, MultipartFile image) {
		String contentType = image.getContentType();
		if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
			throw new RuntimeException("Invalid image file type: " + image.getOriginalFilename());
		}

		if (image.getSize() > 1048576) { // 1048576 bytes = 1MB
			throw new RuntimeException("File size exceeds the maximum permitted size: " + image.getOriginalFilename());
		}

		try {
			String originalFileName = StringUtils.cleanPath(image.getOriginalFilename());
			String extension = FilenameUtils.getExtension(originalFileName);
			String savingFileName = userId.toString() + "." + extension;

			File uploadDir = new File(uploadPath + File.separator + uploadFolder);
			if (!uploadDir.exists())
				uploadDir.mkdir();

			File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
			image.transferTo(destFile);

			return savingFileName;
		} catch (IOException ex) {
			throw new RuntimeException("Failed to store image file: " + image.getOriginalFilename(), ex);
		}
	}

	@Transactional
	public UserDto updateUserName(Long userId, String name) {
		UserEntity userEntity = userRepository.findById(userId).get();

		userEntity.setName(name);

		userEntity = userRepository.save(userEntity);

		UserDto userDto = new UserDto();

		userDto.setEmail(userEntity.getEmail());
		userDto.setName(userEntity.getName());
		userDto.setProfilePhotoUrl(userEntity.getProfilePhotoUrl());

		return userDto;
	}

	@Transactional
	public UserDto updateUserPassword(Long userId, String currentPassword, String newPassword) {
		UserEntity userEntity = userRepository.findById(userId).get();

		if (!passwordEncoder.matches(currentPassword, userEntity.getPassword())) {
			return null;
		}

		userEntity.setPassword(newPassword);
		userEntity.encodePassword(passwordEncoder);

		userEntity = userRepository.save(userEntity);

		UserDto userDto = new UserDto();

		userDto.setEmail(userEntity.getEmail());
		userDto.setName(userEntity.getName());
		userDto.setProfilePhotoUrl(userEntity.getProfilePhotoUrl());

		return userDto;
	}

	public LoginInfoDto me(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);

		LoginInfoDto loginInfoDto = new LoginInfoDto();

		loginInfoDto.setEmail(userEntity.getEmail());
		loginInfoDto.setName(userEntity.getName());
		loginInfoDto.setProfilePhotoUrl(userEntity.getProfilePhotoUrl());

		return loginInfoDto;
	}
}
