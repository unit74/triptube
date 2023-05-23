package com.ssafy.triptube.trips.histories.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;
import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.trips.histories.models.HistoryEntity;
import com.ssafy.triptube.trips.histories.services.HistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/private/trips/histories")
@RequiredArgsConstructor
public class HistoryPrivateController {

	private final HistoryService historyService;

	@DeleteMapping("/{historyId}")
	public ResponseEntity<?> deleteHistory(@PathVariable Long historyId) {
		historyService.deleteHistory(historyId);

		return createResponse(true, "기록 삭제");
	}

	@DeleteMapping("")
	public ResponseEntity<?> deleteHistories(String type) {
		historyService.deleteHistories(getLoginUserId(), HistoryEntity.Type.valueOf(type));

		return createResponse(true, type + " 기록 삭제");
	}
}
