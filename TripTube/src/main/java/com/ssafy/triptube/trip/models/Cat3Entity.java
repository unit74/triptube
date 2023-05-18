package com.ssafy.triptube.trip.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cat3")
public class Cat3Entity {
	@Id
	@Column(name = "cat3_code")
	private String cat3Code;

	@Column(name = "cat3_name", nullable = false)
	private String cat3Name;

	@Column(name = "cat2_code", nullable = false)
	private String cat2Code;
}
