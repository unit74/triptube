package com.ssafy.triptube.trips.categories.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "content_type")
public class ContentTypeEntity {

	@Id
	@Column(name = "content_type_id")
	private Integer contentTypeId;

	@Column(name = "contentTypeName")
	private String contentTypeName;

}
