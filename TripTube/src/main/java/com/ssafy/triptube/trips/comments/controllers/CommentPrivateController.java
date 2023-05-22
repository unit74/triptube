package com.ssafy.triptube.trips.comments.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;
import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.trips.comments.dtos.CommentRequestDto;
import com.ssafy.triptube.trips.comments.services.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/private/trips/comments")
@RequiredArgsConstructor
public class CommentPrivateController {

	private final CommentService commentService;

	@PostMapping("/{contentId}")
	public ResponseEntity<?> commentAttraction(@PathVariable Integer contentId,
			@RequestBody CommentRequestDto commentRequestDto) {
		return createResponse(true, "댓글 달기",
				commentService.commentAttraction(getLoginUserId(), contentId, commentRequestDto.getText()));
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
		boolean success = commentService.deleteComment(getLoginUserId(), commentId);

		return createResponse(success, "댓글 삭제");
	}
}
