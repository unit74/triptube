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
@Entity(name = "cat2")
public class Cat2Entity {
	@Id
	@Column(name = "cat2_code")
	private String cat2Code;

	@Column(name = "cat2_name", nullable = false)
	private String cat2Name;

	@Column(name = "cat1_code", nullable = false)
	private String cat1Code;
}
