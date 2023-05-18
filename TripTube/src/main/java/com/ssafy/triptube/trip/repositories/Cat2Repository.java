package com.ssafy.triptube.trip.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trip.models.Cat2Entity;

public interface Cat2Repository extends JpaRepository<Cat2Entity, String> {
	List<Cat2Entity> findAllByCat1Code(String cat1Code);
}
