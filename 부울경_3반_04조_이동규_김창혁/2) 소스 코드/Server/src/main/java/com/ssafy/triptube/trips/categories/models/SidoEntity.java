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
@Entity(name = "sido")
public class SidoEntity {

	@Id
	@Column(name = "sido_code")
	private Integer sido;

	@Column(name = "sido_name")
	private String sidoName;
}
