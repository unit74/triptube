package com.ssafy.triptube.trips.replies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trips.replies.models.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

}
