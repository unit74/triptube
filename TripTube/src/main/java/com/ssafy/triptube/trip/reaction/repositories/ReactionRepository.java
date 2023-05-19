package com.ssafy.triptube.trip.reaction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trip.reaction.models.ReactionEntity;

public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {
	Long countByTypeAndAttractionInfo_ContentId(ReactionEntity.Type type, Integer contentId);
}
