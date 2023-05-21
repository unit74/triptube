package com.ssafy.triptube.trips.histories.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.support.web.ApiResponseUtil;
import com.ssafy.triptube.trips.histories.models.HistoryEntity;
import com.ssafy.triptube.trips.histories.services.HistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/private/trips/histories")
@RequiredArgsConstructor
public class HistoryPrivateController {

	private final HistoryService historyService;

	@GetMapping("")
	public ResponseEntity<?> getHistories(String type) {
		if (!("VISIT".equals(type) || "SEARCH".equals(type))) {
			return ApiResponseUtil.createResponse(false, "검색 기록은 VISIT, SEARCH만 가능");
		}

		return ApiResponseUtil.createResponse(true, type + " 기록 조회",
				historyService.getHistories(getLoginUserId(), HistoryEntity.Type.valueOf(type)));
	}

	@DeleteMapping("/{historyId}")
	public ResponseEntity<?> deleteHistory(@PathVariable Long historyId) {
		historyService.deleteHistory(historyId);

		return ApiResponseUtil.createResponse(true, "기록 삭제");
	}
}
