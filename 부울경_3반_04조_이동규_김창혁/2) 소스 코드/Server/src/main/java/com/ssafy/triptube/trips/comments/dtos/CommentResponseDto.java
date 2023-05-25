package com.ssafy.triptube.trips.comments.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.triptube.trips.replies.dtos.ReplyResponseDto;
import com.ssafy.triptube.users.dtos.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto {

	private Long commentId;

	private String text;

	private UserDto user;

	private Integer contentId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private List<ReplyResponseDto> replies;

}
