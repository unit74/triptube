package com.ssafy.triptube.trips.libraries.controllers;

import static com.ssafy.triptube.configures.security.utils.SecurityUtil.getLoginUserId;
import static com.ssafy.triptube.support.web.ApiResponseUtil.createResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triptube.trips.libraries.dtos.LibraryRequestDto;
import com.ssafy.triptube.trips.libraries.dtos.LibraryResponseDto;
import com.ssafy.triptube.trips.libraries.services.LibraryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/private/trips/libraries")
@RequiredArgsConstructor
public class LibraryPrivateController {

	private final LibraryService libraryService;

	@GetMapping("/{contentId}")
	public ResponseEntity<?> getLibrary(@PathVariable Integer contentId) {
		LibraryResponseDto libraryResponseDto = libraryService.getLibrary(getLoginUserId(), contentId);

		if (libraryResponseDto == null) {
			return createResponse(false, "보관하지 않은 관광지");
		}

		return createResponse(true, "보관한 관광지", libraryResponseDto);
	}

	@PostMapping("")
	public ResponseEntity<?> saveLibrary(@RequestBody LibraryRequestDto libraryRequestDto) {
		return createResponse(true, "보관",
				libraryService.saveLibrary(getLoginUserId(), libraryRequestDto.getContentId()));
	}

	@DeleteMapping("/{libraryId}")
	public ResponseEntity<?> deleteLibrary(@PathVariable Long libraryId) {
		libraryService.deleteLibrary(libraryId);

		return createResponse(true, "보관함 삭제");
	}
}
