package com.ssafy.triptube.user.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserEntity {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "profile_url", nullable = false)
	private String profileUrl;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	public void createTimeStamps() {
		updatedAt = LocalDateTime.now();
		createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void updateTimeStamps() {
		updatedAt = LocalDateTime.now();
	}
}
