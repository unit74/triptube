package com.ssafy.triptube.trip.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trip.models.Cat2Entity;
import com.ssafy.triptube.trip.repositories.Cat2Repository;

@Service
public class Cat2Service {

	private final Cat2Repository cat2Repository;

	public Cat2Service(Cat2Repository cat2Repository) {
		this.cat2Repository = cat2Repository;
	}

	public List<Cat2Entity> getCat2s(String cat1Code) {
		return cat2Repository.findAllByCat1Code(cat1Code);
	}

}
