package com.ssafy.triptube.trips.attractions.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.support.web.ApiResponseUtil;
import com.ssafy.triptube.trips.attractions.services.AttractionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/trips/attractions")
@RequiredArgsConstructor
public class AttractionPublicController {

	private final AttractionService attractionInfoService;

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
