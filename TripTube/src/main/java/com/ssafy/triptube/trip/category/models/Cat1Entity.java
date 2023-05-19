package com.ssafy.triptube.trip.category.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cat1")
public class Cat1Entity {
	@Id
	@Column(name = "cat1_code")
	private String cat1Code;

	@Column(name = "cat1_name", nullable = false)
	private String cat1Name;
}
