package com.ssafy.triptube.trip.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.support.web.ApiResponseUtil;
import com.ssafy.triptube.trip.services.Cat2Service;

@RestController
@RequestMapping("/api/v1/trip/category/cat2")
public class Cat2Controller {

	private final Cat2Service cat2Service;

	public Cat2Controller(Cat2Service cat2Service) {
		this.cat2Service = cat2Service;
	}

	@GetMapping("")
	public ResponseEntity<?> getCat2s(@RequestParam String cat1Code) {
		return ApiResponseUtil.createResponse(true, "중분류 가져오기", cat2Service.getCat2s(cat1Code));
	}
}
