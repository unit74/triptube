package com.ssafy.triptube.trips.categories.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class GugunPK implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer gugun;
	private Integer sido;
}
