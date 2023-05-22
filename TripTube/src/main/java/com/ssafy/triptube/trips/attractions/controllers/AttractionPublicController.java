package com.ssafy.triptube.trips.attractions.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;
import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> getRandomAttractions() {
		return createResponse(true, "랜덤 관광지 12개 추출", attractionInfoService.getRandomAttractions());
	}

	@GetMapping("/{contentId}")
	public ResponseEntity<?> getAttractionDetail(@PathVariable Integer contentId) {
		AttractionInfoDto attractionInfoDto = attractionInfoService.getAttractionDetail(contentId);

		Long userId = getLoginUserId();
		if (userId != null) {
			historyService.visitAttraction(getLoginUserId(), contentId);
		}

		return createResponse(true, contentId.toString(), attractionInfoDto);
	}

	@GetMapping("/searches")
	public ResponseEntity<?> getSearchAttractions(String searchText, Integer page) {
		List<AttractionInfoDto> attractionInfoDto = attractionInfoService.getSearchAttractions(searchText, page);

		Long userId = getLoginUserId();
		if (userId != null) {
			historyService.searchAttraction(userId, searchText);
		}

		return createResponse(true, "관광지 검색", attractionInfoDto);
	}

	@GetMapping("/hots")
	public ResponseEntity<?> getHotAttractions(Integer page) {
		return createResponse(true, "핫플레이스", attractionInfoService.getHotAttractions(page));
	}

}
