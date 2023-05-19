package com.ssafy.triptube.trip.reaction.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDto {
	private Long userId;
	private Integer contentId;
	private String type;
}
