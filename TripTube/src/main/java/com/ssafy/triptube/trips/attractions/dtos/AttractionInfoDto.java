package com.ssafy.triptube.trips.attractions.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttractionInfoDto {

	private Integer contentId;

	private Integer contentTypeId;

	private String title;

	private String addr1;

	private String addr2;

	private String zipcode;

	private String tel;

	private String firstImage;

	private String firstImage2;

	private Integer readcount;

	private Integer sidoCode;

	private Integer gugunCode;

	private BigDecimal latitude;

	private BigDecimal longitude;

	private String mlevel;

	private String overview;

	private Long likes;

	private Long dislikes;

	private Long comments;
}
