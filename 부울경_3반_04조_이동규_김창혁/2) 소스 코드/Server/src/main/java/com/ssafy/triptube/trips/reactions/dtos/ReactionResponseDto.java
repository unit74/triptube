package com.ssafy.triptube.trips.reactions.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.triptube.trips.attractions.dtos.AttractionInfoDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReactionResponseDto {

	private Long reactionId;

	private Integer contentId;

	private String type;

	private LocalDateTime updatedAt;

	private LocalDateTime createdAt;

	private AttractionInfoDto attractionInfo;

}
