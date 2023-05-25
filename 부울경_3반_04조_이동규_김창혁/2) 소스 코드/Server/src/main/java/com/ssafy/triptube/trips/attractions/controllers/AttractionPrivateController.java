package com.ssafy.triptube.trips.attractions.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;
import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.trips.histories.models.HistoryEntity;
import com.ssafy.triptube.trips.histories.services.HistoryService;
import com.ssafy.triptube.trips.libraries.services.LibraryService;
import com.ssafy.triptube.trips.reactions.services.ReactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/private/trips/attractions")
@RequiredArgsConstructor
public class AttractionPrivateController {

	private final HistoryService historyService;

	private final ReactionService reactionService;

	private final LibraryService libraryService;

	@GetMapping("/histories")
	public ResponseEntity<?> getHistories(String type, Integer page) {
		if (!("VISIT".equals(type) || "SEARCH".equals(type))) {
			return createResponse(false, "검색 기록은 VISIT, SEARCH만 가능");
		}

		return createResponse(true, type + " 기록 조회",
				historyService.getHistories(getLoginUserId(), HistoryEntity.Type.valueOf(type), page));
	}

	@GetMapping("/likes")
	public ResponseEntity<?> getLikeAttractions(Integer page) {
		return createResponse(true, "좋아요한 관광지", reactionService.getLikeAttractions(getLoginUserId(), page));
	}

	@GetMapping("/libraries")
	public ResponseEntity<?> getSaveAttractions(Integer page) {
		return createResponse(true, "보관한 관광지", libraryService.getSaveLibraries(getLoginUserId(), page));
	}

}
