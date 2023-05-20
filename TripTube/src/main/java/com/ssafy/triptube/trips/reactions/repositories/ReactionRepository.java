package com.ssafy.triptube.trips.reactions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trips.reactions.models.ReactionEntity;

public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {
	Long countByTypeAndAttractionInfo_ContentId(ReactionEntity.Type type, Integer contentId);
}
