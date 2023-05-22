package com.ssafy.triptube.trips.categories.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trips.categories.models.SidoEntity;
import com.ssafy.triptube.trips.categories.repositories.SidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SidoService {

	private final SidoRepository sidoRepository;

	public List<SidoEntity> getSidos() {
		return sidoRepository.findAll();
	}
}
