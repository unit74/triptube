package com.ssafy.triptube.trip.category.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.support.web.ApiResponseUtil;
import com.ssafy.triptube.trip.category.services.Cat3Service;

@RestController
@RequestMapping("/api/v1/trip/category/cat3")
public class Cat3Controller {

	private final Cat3Service cat3Service;

	public Cat3Controller(Cat3Service cat3Service) {
		this.cat3Service = cat3Service;
	}

	@GetMapping("")
	public ResponseEntity<?> getCat3s(@RequestParam String cat2Code) {
		return ApiResponseUtil.createResponse(true, "소분류 가져오기", cat3Service.getCat3s(cat2Code));
	}
}
