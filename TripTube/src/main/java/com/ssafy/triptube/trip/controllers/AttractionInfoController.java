package com.ssafy.triptube.trip.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.support.web.ApiResponseUtil;
import com.ssafy.triptube.trip.services.AttractionInfoService;

@RestController
@RequestMapping("/api/v1/trip")
public class AttractionInfoController {

	private final AttractionInfoService attractionInfoService;

	public AttractionInfoController(AttractionInfoService attractionInfoService) {
		this.attractionInfoService = attractionInfoService;
	}

	@GetMapping("")
	public ResponseEntity<?> findRandomAttractionInfos() {
		return ApiResponseUtil.createResponse(true, "랜덤 관광지 10개 추출", attractionInfoService.findRandomAttractionInfos());
	}
}
