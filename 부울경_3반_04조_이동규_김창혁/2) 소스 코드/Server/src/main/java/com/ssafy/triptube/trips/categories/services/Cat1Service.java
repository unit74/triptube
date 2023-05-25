package com.ssafy.triptube.trips.categories.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trips.categories.models.Cat1Entity;
import com.ssafy.triptube.trips.categories.repositories.Cat1Repository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Cat1Service {

	private final Cat1Repository cat1Repository;

	public List<Cat1Entity> getCat1s() {
		return cat1Repository.findAll();
	}

}
