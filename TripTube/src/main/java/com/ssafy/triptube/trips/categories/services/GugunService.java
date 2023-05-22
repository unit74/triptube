package com.ssafy.triptube.trips.categories.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trips.categories.models.GugunEntity;
import com.ssafy.triptube.trips.categories.repositories.GugunRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GugunService {

	private final GugunRepository gugunRepository;

	public List<GugunEntity> getGuguns() {
		return gugunRepository.findAll();
	}
}
