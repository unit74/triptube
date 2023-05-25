package com.ssafy.triptube.trips.categories.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trips.categories.models.GugunEntity;

public interface GugunRepository extends JpaRepository<GugunEntity, Long> {
	List<GugunEntity> findAllBySido(Integer sido);
}
