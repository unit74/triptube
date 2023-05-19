package com.ssafy.triptube.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.user.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);
}
