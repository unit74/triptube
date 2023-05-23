package com.ssafy.triptube.trips.categories.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.triptube.trips.categories.models.ContentTypeEntity;

public interface ContentTypeRepository extends JpaRepository<ContentTypeEntity, Integer> {

}
