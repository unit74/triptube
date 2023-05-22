package com.ssafy.triptube.trips.attractions.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;
import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.trips.attractions.services.AttractionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/private/trips/attractions")
@RequiredArgsConstructor
public class AttractionPrivateController {

	private final AttractionService attractionInfoService;

	@GetMapping("/likes")
	public ResponseEntity<?> getLikeAttractions() {
		return createResponse(true, "좋아요한 관광지", attractionInfoService.getLikeAttractions(getLoginUserId()));
	}
}
