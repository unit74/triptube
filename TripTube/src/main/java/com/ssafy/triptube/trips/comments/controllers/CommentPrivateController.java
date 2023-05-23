package com.ssafy.triptube.trips.comments.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;
import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.trips.comments.dtos.CommentRequestDto;
import com.ssafy.triptube.trips.comments.dtos.CommentResponseDto;
import com.ssafy.triptube.trips.comments.services.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/private/trips/comments")
@RequiredArgsConstructor
public class CommentPrivateController {

	private final CommentService commentService;

	@GetMapping("")
	public ResponseEntity<?> getComments() {
		return createResponse(true, "댓글 기록", commentService.getComments(getLoginUserId()));
	}

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

	@PutMapping("/{commentId}")
	public ResponseEntity<?> updateCommnet(@PathVariable Long commentId,
			@RequestBody CommentRequestDto commentRequestDto) {
		CommentResponseDto commentResponseDto = commentService.updateComment(getLoginUserId(), commentId,
				commentRequestDto.getText());

		if (commentResponseDto == null) {
			return createResponse(false, "수정할 내용이 없거나 다른 사람의 아이디를 수정");
		}

		return createResponse(true, "댓글 갱신", commentResponseDto);
	}
}
