package com.ssafy.triptube.trips.reactions.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trips.reactions.models.ReactionEntity;

public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {
	Long countByTypeAndAttractionInfo_ContentId(ReactionEntity.Type type, Integer contentId);

	ReactionEntity findByUser_UserIdAndAttractionInfo_ContentId(Long userId, Integer contentId);

	void deleteByUser_UserIdAndAttractionInfo_ContentId(Long userId, Integer contentId);

	Slice<ReactionEntity> findByUser_UserId(Long userId, Pageable pageable);

}
