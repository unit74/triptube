package com.ssafy.triptube.trip.attraction.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.support.web.ApiResponseUtil;
import com.ssafy.triptube.trip.attraction.services.AttractionService;

@RestController
@RequestMapping("/api/v1/trip")
@CrossOrigin
public class AttractionController {

	private final AttractionService attractionInfoService;

	public AttractionController(AttractionService attractionInfoService) {
		this.attractionInfoService = attractionInfoService;
	}

	@GetMapping("")
	public ResponseEntity<?> findRandomAttractionInfos() {
		return ApiResponseUtil.createResponse(true, "랜덤 관광지 12개 추출", attractionInfoService.findRandomAttractionInfos());
	}

	@GetMapping("/{contentId}")
	public ResponseEntity<?> getAttractionDetail(@PathVariable Integer contentId) {
		return ApiResponseUtil.createResponse(true, contentId.toString(),
				attractionInfoService.getAttractionDetail(contentId));
	}
}
