package com.ssafy.triptube.trips.histories.dtos;

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
public class HistoryDto {
	private Long historyId;

	private String searchText;

	private AttractionInfoDto attractionInfo;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
