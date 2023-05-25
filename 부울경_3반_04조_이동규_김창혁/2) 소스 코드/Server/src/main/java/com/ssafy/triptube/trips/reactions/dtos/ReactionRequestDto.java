package com.ssafy.triptube.trips.reactions.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionRequestDto {
	private Integer contentId;
	private String type;
}
