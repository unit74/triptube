package com.ssafy.triptube.trips.comments.controllers;

import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.trips.comments.services.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/trips/comments")
@RequiredArgsConstructor
public class CommentPublicController {

	private final CommentService commentService;

	@GetMapping("/{contentId}")
	public ResponseEntity<?> getAttractionComments(@PathVariable Integer contentId) {
		return createResponse(true, "댓글", commentService.getAttractionComments(contentId));
	}
}
