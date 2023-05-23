package com.ssafy.triptube.trips.libraries.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.ssafy.triptube.trips.attractions.models.AttractionInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "libraries")
public class LibraryEntity {

	@Id
	@Column(name = "library_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long libraryId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@ManyToOne
	@JoinColumn(name = "content_id", nullable = false)
	private AttractionInfoEntity attractionInfo;

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
