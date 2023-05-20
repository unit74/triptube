package com.ssafy.triptube.trips.categories.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trips.categories.models.Cat3Entity;
import com.ssafy.triptube.trips.categories.repositories.Cat3Repository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Cat3Service {

	private final Cat3Repository cat3Repository;

	public List<Cat3Entity> getCat3s(String cat2Code) {
		return cat3Repository.findAllByCat2Code_Cat2Code(cat2Code);
	}

}
