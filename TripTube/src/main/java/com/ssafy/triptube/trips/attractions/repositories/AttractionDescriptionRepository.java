package com.ssafy.triptube.trips.attractions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trips.attractions.models.AttractionDescriptionEntity;

public interface AttractionDescriptionRepository extends JpaRepository<AttractionDescriptionEntity, Integer> {

}
