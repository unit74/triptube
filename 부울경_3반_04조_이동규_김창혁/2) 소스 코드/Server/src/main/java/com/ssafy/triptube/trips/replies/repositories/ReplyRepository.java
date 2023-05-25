package com.ssafy.triptube.trips.replies.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trips.replies.models.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
	List<ReplyEntity> findAllByUser_UserId(Long userId);
}
