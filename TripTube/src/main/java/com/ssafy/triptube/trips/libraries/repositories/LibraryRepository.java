package com.ssafy.triptube.trips.libraries.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trips.libraries.models.LibraryEntity;

public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
	Slice<LibraryEntity> findByUserId(Long userId, Pageable pageable);

	LibraryEntity findByUserIdAndAttractionInfo_ContentId(Long userId, Integer contentId);
}
