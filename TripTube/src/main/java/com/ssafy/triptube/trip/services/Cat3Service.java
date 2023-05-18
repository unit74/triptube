package com.ssafy.triptube.trip.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trip.models.Cat3Entity;
import com.ssafy.triptube.trip.repositories.Cat3Repository;

@Service
public class Cat3Service {

	private final Cat3Repository cat3Repository;

	public Cat3Service(Cat3Repository cat3Repository) {
		this.cat3Repository = cat3Repository;
	}

	public List<Cat3Entity> getCat3s(String cat2Code) {
		return cat3Repository.findAllByCat2Code(cat2Code);
	}

}
