package com.ssafy.triptube.trips.reactions.controllers;

import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/private/trip/reaction")
@RequiredArgsConstructor
public class ReactionPrivateController {

	private final ReactionService reactionService;

	@PostMapping("")
	public ResponseEntity<?> react(@RequestBody ReactionDto reactionDto) {
		return ApiResponseUtil.createResponse(true, reactionDto.getType(),
				reactionService.react(reactionDto.getContentId(), ReactionEntity.Type.valueOf(reactionDto.getType())));
	}
}
