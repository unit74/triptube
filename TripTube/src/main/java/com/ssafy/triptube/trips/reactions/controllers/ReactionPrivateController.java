package com.ssafy.triptube.trips.reactions.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;
import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.trips.reactions.dtos.ReactionDto;
import com.ssafy.triptube.trips.reactions.models.ReactionEntity;
import com.ssafy.triptube.trips.reactions.services.ReactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/private/trips/reactions")
@RequiredArgsConstructor
public class ReactionPrivateController {

	private final ReactionService reactionService;

	@GetMapping("/{contentId}")
	public ResponseEntity<?> getReaction(@PathVariable Integer contentId) {
		return createResponse(true, "리액션", reactionService.getReaction(getLoginUserId(), contentId));
	}

	@PostMapping("")
	public ResponseEntity<?> react(@RequestBody ReactionDto reactionDto) {
		if (!("LIKE".equals(reactionDto.getType()) || "DISLIKE".equals(reactionDto.getType()))) {
			return createResponse(false, "리액션은 LIKE, DISLIKE만 가능");
		}

		reactionService.react(getLoginUserId(), reactionDto.getContentId(),
				ReactionEntity.Type.valueOf(reactionDto.getType()));

		return createResponse(true, reactionDto.getType());
	}

	@DeleteMapping("/{contentId}")
	public ResponseEntity<?> cancelReaction(@PathVariable Integer contentId) {
		return createResponse(true, "리액션 취소", reactionService.cancelReaction(getLoginUserId(), contentId));
	}
}
