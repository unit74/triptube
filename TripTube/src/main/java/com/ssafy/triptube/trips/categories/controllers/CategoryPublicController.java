package com.ssafy.triptube.trips.categories.controllers;

import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.trips.categories.services.Cat1Service;
import com.ssafy.triptube.trips.categories.services.Cat2Service;
import com.ssafy.triptube.trips.categories.services.Cat3Service;
import com.ssafy.triptube.trips.categories.services.ContentTypeService;
import com.ssafy.triptube.trips.categories.services.GugunService;
import com.ssafy.triptube.trips.categories.services.SidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/trips/categories")
@RequiredArgsConstructor
public class CategoryPublicController {

	private final Cat1Service cat1Service;

	private final Cat2Service cat2Service;

	private final Cat3Service cat3Service;

	private final GugunService gugunService;

	private final SidoService sidoService;

	private final ContentTypeService contentTypeService;

	@GetMapping("/cat1")
	public ResponseEntity<?> getCat1s() {
		return createResponse(true, "대분류 가져오기", cat1Service.getCat1s());
	}

	@GetMapping("/cat2")
	public ResponseEntity<?> getCat2s(String cat1Code) {
		return createResponse(true, "중분류 가져오기", cat2Service.getCat2s(cat1Code));
	}

	@GetMapping("/cat3")
	public ResponseEntity<?> getCat3s(String cat2Code) {
		return createResponse(true, "소분류 가져오기", cat3Service.getCat3s(cat2Code));
	}

	@GetMapping("/guguns")
	public ResponseEntity<?> getGuguns(Integer sidoCode) {
		return createResponse(true, "구군 가져오기", gugunService.getGuguns(sidoCode));
	}

	@GetMapping("/sidos")
	public ResponseEntity<?> getSidos() {
		return createResponse(true, "시도 가져오기", sidoService.getSidos());
	}

	@GetMapping("/contents")
	public ResponseEntity<?> getContentTypes() {
		return createResponse(true, "컨텐츠 가져오기", contentTypeService.getContentTypes());
	}

}
