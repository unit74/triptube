package com.ssafy.triptube.trips.histories.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trips.histories.models.HistoryEntity;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
	HistoryEntity findByUser_UserIdAndAttractionInfo_ContentId(Long userId, Integer contentId);

	HistoryEntity findByUser_UserIdAndSearchText(Long userId, String searchText);

	Slice<HistoryEntity> findByUser_UserIdAndTypeOrderByUpdatedAtDesc(Long userId, HistoryEntity.Type type,
			Pageable pageable);
}
