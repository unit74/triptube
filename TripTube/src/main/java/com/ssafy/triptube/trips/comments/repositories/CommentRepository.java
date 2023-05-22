package com.ssafy.triptube.trips.comments.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trips.comments.models.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	List<CommentEntity> findAllByContentId(Integer contentId);

	Long countByContentId(Integer contentId);
}
