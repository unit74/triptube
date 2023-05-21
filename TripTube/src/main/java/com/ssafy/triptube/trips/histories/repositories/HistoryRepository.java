package com.ssafy.triptube.trips.histories.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trips.histories.models.HistoryEntity;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
	HistoryEntity findByUser_UserIdAndAttractionInfo_ContentId(Long userId, Integer contentId);

	HistoryEntity findByUser_UserIdAndSearchText(Long userId, String searchText);

	List<HistoryEntity> findAllByUser_UserIdAndTypeOrderByUpdatedAtDesc(Long userId, HistoryEntity.Type type);
}
