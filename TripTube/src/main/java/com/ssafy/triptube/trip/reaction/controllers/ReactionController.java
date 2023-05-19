package com.ssafy.triptube.trip.reaction.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.support.web.ApiResponseUtil;
import com.ssafy.triptube.trip.reaction.dtos.ReactionDto;
import com.ssafy.triptube.trip.reaction.models.ReactionEntity;
import com.ssafy.triptube.trip.reaction.services.ReactionService;

@RestController
@RequestMapping("/api/v1/trip/reaction")
public class ReactionController {

	private final ReactionService reactionService;

	public ReactionController(ReactionService reactionService) {
		this.reactionService = reactionService;
	}

	@PostMapping("")
	public ResponseEntity<?> react(@RequestBody ReactionDto reactionDto) {
		return ApiResponseUtil.createResponse(true, reactionDto.getType(),
				reactionService.react(reactionDto.getUserId(), reactionDto.getContentId(),
						ReactionEntity.Type.valueOf(reactionDto.getType())));
	}
}
