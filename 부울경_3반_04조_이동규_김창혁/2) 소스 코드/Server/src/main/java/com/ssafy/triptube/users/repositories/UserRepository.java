package com.ssafy.triptube.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.users.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);

	boolean existsByEmail(String email);
}
