package com.ssafy.triptube.trip.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.trip.models.Cat1Entity;
import com.ssafy.triptube.trip.services.Cat1Service;

@RestController
@RequestMapping("/api/v1/trip/category/cat1")
public class Cat1Controller {

	private final Cat1Service cat1Service;

	public Cat1Controller(Cat1Service cat1Service) {
		this.cat1Service = cat1Service;
	}

	@GetMapping("")
	public List<Cat1Entity> getCat1s() {
		return cat1Service.getCat1s();
	}
}
