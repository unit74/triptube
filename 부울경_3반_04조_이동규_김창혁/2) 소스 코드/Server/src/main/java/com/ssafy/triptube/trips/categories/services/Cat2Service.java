package com.ssafy.triptube.trips.categories.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trips.categories.models.Cat2Entity;
import com.ssafy.triptube.trips.categories.repositories.Cat2Repository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Cat2Service {

	private final Cat2Repository cat2Repository;

	public List<Cat2Entity> getCat2s(String cat1Code) {
		return cat2Repository.findAllByCat1Code_Cat1Code(cat1Code);
	}

}
