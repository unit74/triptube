package com.ssafy.triptube.trip.attraction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trip.attraction.models.AttractionDescriptionEntity;

public interface AttractionDescriptionRepository extends JpaRepository<AttractionDescriptionEntity, Integer> {

}
