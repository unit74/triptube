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
@Entity(name = "cat1")
public class Cat1Entity {
	@Id
	private String cat1Code;
	@Column(nullable = false)
	private String cat1Name;
}
