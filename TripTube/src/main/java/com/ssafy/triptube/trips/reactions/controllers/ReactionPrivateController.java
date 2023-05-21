package com.ssafy.triptube.trips.reactions.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.support.web.ApiResponseUtil;
import com.ssafy.triptube.trips.reactions.dtos.ReactionDto;
import com.ssafy.triptube.trips.reactions.models.ReactionEntity;
import com.ssafy.triptube.trips.reactions.services.ReactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/private/trips/reactions")
@RequiredArgsConstructor
public class ReactionPrivateController {

	private final ReactionService reactionService;

	@PostMapping("")
	public ResponseEntity<?> react(@RequestBody ReactionDto reactionDto) {
		if (!("LIKE".equals(reactionDto.getType()) || "DISLIKE".equals(reactionDto.getType()))) {
			return ApiResponseUtil.createResponse(false, "리액션은 LIKE, DISLIKE만 가능");
		}

		return ApiResponseUtil.createResponse(true, reactionDto.getType(), reactionService.react(getLoginUserId(),
				reactionDto.getContentId(), ReactionEntity.Type.valueOf(reactionDto.getType())));
	}

	@DeleteMapping("/{contentId}")
	public ResponseEntity<?> cancelReaction(@PathVariable Integer contentId) {
		return ApiResponseUtil.createResponse(true, "리액션 취소",
				reactionService.cancelReaction(getLoginUserId(), contentId));

	}
}
