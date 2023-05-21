package com.ssafy.triptube.trips.categories.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.support.web.ApiResponseUtil;
import com.ssafy.triptube.trips.categories.services.Cat1Service;
import com.ssafy.triptube.trips.categories.services.Cat2Service;
import com.ssafy.triptube.trips.categories.services.Cat3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/trips/categories")
@RequiredArgsConstructor
public class CategoryPublicController {

	private final Cat1Service cat1Service;

	private final Cat2Service cat2Service;

	private final Cat3Service cat3Service;

	@GetMapping("/cat1")
	public ResponseEntity<?> getCat1s() {
		return ApiResponseUtil.createResponse(true, "대분류 가져오기", cat1Service.getCat1s());
	}

	@GetMapping("/cat2")
	public ResponseEntity<?> getCat2s(@RequestParam String cat1Code) {
		return ApiResponseUtil.createResponse(true, "중분류 가져오기", cat2Service.getCat2s(cat1Code));
	}

	@GetMapping("/cat3")
	public ResponseEntity<?> getCat3s(@RequestParam String cat2Code) {
		return ApiResponseUtil.createResponse(true, "소분류 가져오기", cat3Service.getCat3s(cat2Code));
	}
}
