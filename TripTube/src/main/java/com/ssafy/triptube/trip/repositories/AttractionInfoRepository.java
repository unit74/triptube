package com.ssafy.triptube.trip.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.triptube.trip.models.AttractionInfoEntity;

public interface AttractionInfoRepository extends JpaRepository<AttractionInfoEntity, Integer> {
	@Query(value = "SELECT * FROM attraction_info ORDER BY RAND() LIMIT 10", nativeQuery = true)
	List<AttractionInfoEntity> findRandomAttractionInfos();
}