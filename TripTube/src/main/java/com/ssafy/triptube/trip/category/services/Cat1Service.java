package com.ssafy.triptube.trip.category.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trip.category.models.Cat1Entity;
import com.ssafy.triptube.trip.category.repositories.Cat1Repository;

@Service
public class Cat1Service {

	private final Cat1Repository cat1Repository;

	public Cat1Service(Cat1Repository cat1Repository) {
		this.cat1Repository = cat1Repository;
	}

	public List<Cat1Entity> getCat1s() {
		return cat1Repository.findAll();
	}

}
