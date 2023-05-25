package com.ssafy.triptube.trips.replies.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.triptube.users.dtos.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyResponseDto {

	private Long replyId;

	private String text;

	private UserDto user;

	private Long commentId;

	private Integer contentId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
