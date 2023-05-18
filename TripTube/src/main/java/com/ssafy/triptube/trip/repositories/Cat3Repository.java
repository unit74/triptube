package com.ssafy.triptube.trip.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trip.models.Cat3Entity;

public interface Cat3Repository extends JpaRepository<Cat3Entity, String> {
	List<Cat3Entity> findAllByCat2Code(String cat2Code);
}
