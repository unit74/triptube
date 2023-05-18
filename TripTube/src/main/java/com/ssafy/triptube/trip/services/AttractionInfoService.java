package com.ssafy.triptube.trip.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trip.models.AttractionInfoEntity;
import com.ssafy.triptube.trip.repositories.AttractionInfoRepository;

@Service
public class AttractionInfoService {

	private final AttractionInfoRepository attractionInfoRepository;

	public AttractionInfoService(AttractionInfoRepository attractionInfoRepository) {
		this.attractionInfoRepository = attractionInfoRepository;
	}

	public List<AttractionInfoEntity> findRandomAttractionInfos() {
		return attractionInfoRepository.findRandomAttractionInfos();
	}
}
