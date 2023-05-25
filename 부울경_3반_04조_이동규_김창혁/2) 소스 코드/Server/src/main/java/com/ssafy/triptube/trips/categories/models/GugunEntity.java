package com.ssafy.triptube.trips.categories.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "gugun")
@IdClass(GugunPK.class)
public class GugunEntity {

	@Id
	@Column(name = "gugun_code")
	private Integer gugun;

	@Column(name = "gugun_name")
	private String gugunName;

	@Id
	@Column(name = "sido_code")
	private Integer sido;

}
