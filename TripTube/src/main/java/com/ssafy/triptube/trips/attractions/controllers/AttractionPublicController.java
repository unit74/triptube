package com.ssafy.triptube.trips.attractions.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.support.web.ApiResponseUtil;
import com.ssafy.triptube.trips.attractions.dtos.AttractionInfoDto;
import com.ssafy.triptube.trips.attractions.services.AttractionService;
import com.ssafy.triptube.trips.histories.services.HistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/trips/attractions")
@RequiredArgsConstructor
public class AttractionPublicController {

	private final AttractionService attractionInfoService;

	private final HistoryService historyService;

	@GetMapping("")
	public ResponseEntity<?> findRandomAttractionInfos() {
		return ApiResponseUtil.createResponse(true, "랜덤 관광지 12개 추출", attractionInfoService.findRandomAttractionInfos());
	}

	@GetMapping("/{contentId}")
	public ResponseEntity<?> getAttractionDetail(@PathVariable Integer contentId) {

		System.out.println("contentId : "+contentId);
		AttractionInfoDto attractionInfoDto = attractionInfoService.getAttractionDetail(contentId);
		

		Long userId = getLoginUserId();
		if (userId != null) {
			historyService.visitAttraction(getLoginUserId(), contentId);
		}

		return ApiResponseUtil.createResponse(true, contentId.toString(), attractionInfoDto);
	}
}
