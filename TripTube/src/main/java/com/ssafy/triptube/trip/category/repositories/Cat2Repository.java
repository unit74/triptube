package com.ssafy.triptube.trip.category.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trip.category.models.Cat2Entity;

public interface Cat2Repository extends JpaRepository<Cat2Entity, String> {
	List<Cat2Entity> findAllByCat1Code_Cat1Code(String cat1Code);
}
