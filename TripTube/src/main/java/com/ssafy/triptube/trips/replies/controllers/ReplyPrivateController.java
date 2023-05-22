package com.ssafy.triptube.trips.replies.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;
import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.trips.replies.dtos.ReplyRequestDto;
import com.ssafy.triptube.trips.replies.services.ReplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/private/trips/replies")
@RequiredArgsConstructor
public class ReplyPrivateController {

	private final ReplyService replyService;

	@PostMapping("/{commentId}")
	public ResponseEntity<?> commentAttraction(@PathVariable Long commentId,
			@RequestBody ReplyRequestDto replyRequestDto) {
		return createResponse(true, "답글 달기",
				replyService.replyComment(getLoginUserId(), commentId, replyRequestDto.getText()));
	}

	@DeleteMapping("/{replyId}")
	public ResponseEntity<?> deleteReply(@PathVariable Long replyId) {
		boolean success = replyService.deleteReply(getLoginUserId(), replyId);

		return createResponse(success, "답글 삭제");
	}
}
