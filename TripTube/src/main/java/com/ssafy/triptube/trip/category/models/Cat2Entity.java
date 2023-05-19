package com.ssafy.triptube.trip.category.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

	@ManyToOne
	@JoinColumn(name = "cat1_code")
	private Cat1Entity cat1Code;
}
