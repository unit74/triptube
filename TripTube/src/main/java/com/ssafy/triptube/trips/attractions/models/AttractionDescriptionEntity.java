package com.ssafy.triptube.trips.attractions.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "attraction_description")
public class AttractionDescriptionEntity {

	@Id
	@Column(name = "content_id")
	private Integer contentId;

	@Column(name = "homepage")
	private String homepage;

	@Column(name = "overview")
	private String overview;

	@Column(name = "telname")
	private String telname;
}
