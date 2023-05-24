package com.ssafy.triptube.users.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.triptube.configures.jwt.JwtTokenProvider;
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

	private final JwtTokenProvider jwtTokenProvider;

	@Value("${app.fileupload.uploadDir}")
	private String uploadFolder;

	@Value("${app.fileupload.uploadPath}")
	private String uploadPath;

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
		loginInfoDto.setEmail(userEntity.getEmail());
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
		loginInfoDto.setEmail(userEntity.getEmail());
		loginInfoDto.setName(userEntity.getName());
		loginInfoDto.setProfilePhotoUrl(userEntity.getProfilePhotoUrl());

		return loginInfoDto;
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

	public InputStream getProfileImage(String filename) throws IOException {
		Path file = Paths.get(uploadPath + File.separator + uploadFolder, filename);
		InputStreamResource resource = new InputStreamResource(Files.newInputStream(file));

		return resource.getInputStream();
	}

	@Transactional
	public UserDto updateUser(Long userId, String email, String password, String name) {
		UserEntity userEntity = userRepository.findById(userId).get();

		if (email != null) {
			userEntity.setEmail(email);
		}
		if (password != null) {
			userEntity.setPassword(password);
		}
		if (name != null) {
			userEntity.setName(name);
		}

		userEntity = userRepository.save(userEntity);

		UserDto userDto = new UserDto();

		userDto.setEmail(userEntity.getEmail());
		userDto.setName(userEntity.getName());
		userDto.setProfilePhotoUrl(userEntity.getProfilePhotoUrl());

		return userDto;
	}
}
