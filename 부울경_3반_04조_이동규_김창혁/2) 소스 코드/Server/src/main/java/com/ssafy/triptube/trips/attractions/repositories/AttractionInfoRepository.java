package com.ssafy.triptube.trips.attractions.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.triptube.trips.attractions.models.AttractionInfoEntity;

public interface AttractionInfoRepository extends JpaRepository<AttractionInfoEntity, Integer> {
	@Query(value = "SELECT * FROM attraction_info ORDER BY RAND() LIMIT 12", nativeQuery = true)
	List<AttractionInfoEntity> findRandomAttractionInfos();

	Slice<AttractionInfoEntity> findByTitleLike(String title, Pageable pageable);

	Slice<AttractionInfoEntity> findAllByOrderByReadcountDesc(Pageable pageable);

}
