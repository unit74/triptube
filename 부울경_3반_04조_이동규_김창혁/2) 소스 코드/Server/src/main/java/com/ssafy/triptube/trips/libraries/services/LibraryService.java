package com.ssafy.triptube.trips.libraries.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.ssafy.triptube.trips.attractions.dtos.AttractionInfoDto;
import com.ssafy.triptube.trips.attractions.models.AttractionInfoEntity;
import com.ssafy.triptube.trips.attractions.repositories.AttractionInfoRepository;
import com.ssafy.triptube.trips.libraries.dtos.LibraryResponseDto;
import com.ssafy.triptube.trips.libraries.models.LibraryEntity;
import com.ssafy.triptube.trips.libraries.repositories.LibraryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibraryService {

	private final LibraryRepository libraryRepository;

	private final AttractionInfoRepository attractionInfoRepository;

	public LibraryResponseDto getLibrary(Long userId, Integer contentId) {
		LibraryEntity libraryEntity = libraryRepository.findByUserIdAndAttractionInfo_ContentId(userId, contentId);

		if (libraryEntity == null) {
			return null;
		}

		LibraryResponseDto libraryResponseDto = new LibraryResponseDto();

		libraryResponseDto.setLibraryId(libraryEntity.getLibraryId());
		libraryResponseDto.setCreatedAt(libraryEntity.getCreatedAt());
		libraryResponseDto.setUpdatedAt(libraryEntity.getUpdatedAt());

		return libraryResponseDto;
	}

	public List<LibraryResponseDto> getSaveLibraries(Long userId, Integer page) {
		List<LibraryResponseDto> libraryResponseDtos = new ArrayList<>();

		PageRequest pageRequest = PageRequest.of(page - 1, 12);

		Slice<LibraryEntity> libraryEntitySlice = libraryRepository.findByUserId(userId, pageRequest);

		for (LibraryEntity libraryEntity : libraryEntitySlice.getContent()) {
			LibraryResponseDto libraryResponseDto = new LibraryResponseDto();

			AttractionInfoDto attractionInfoDto = new AttractionInfoDto();

			AttractionInfoEntity attractionInfoEntity = libraryEntity.getAttractionInfo();

			attractionInfoDto.setContentId(attractionInfoEntity.getContentId());
			attractionInfoDto.setTitle(attractionInfoEntity.getTitle());
			attractionInfoDto.setAddr1(attractionInfoEntity.getAddr1());
			attractionInfoDto.setFirstImage(attractionInfoEntity.getFirstImage());
			attractionInfoDto.setFirstImage2(attractionInfoEntity.getFirstImage2());
			attractionInfoDto.setReadcount(attractionInfoEntity.getReadcount());

			libraryResponseDto.setLibraryId(libraryEntity.getLibraryId());
			libraryResponseDto.setAttractionInfo(attractionInfoDto);
			libraryResponseDto.setCreatedAt(libraryEntity.getCreatedAt());
			libraryResponseDto.setUpdatedAt(libraryEntity.getUpdatedAt());

			libraryResponseDtos.add(libraryResponseDto);
		}

		return libraryResponseDtos;
	}

	public LibraryResponseDto saveLibrary(Long userId, Integer contentId) {
		LibraryEntity libraryEntity = libraryRepository.findByUserIdAndAttractionInfo_ContentId(userId, contentId);

		if (libraryEntity == null) {
			libraryEntity = new LibraryEntity();
		}

		if (libraryEntity.getAttractionInfo() == null) {
			libraryEntity.setAttractionInfo(attractionInfoRepository.findById(contentId).get());
		}

		libraryEntity.setUserId(userId);
		libraryEntity.setUpdatedAt(null);

		libraryEntity = libraryRepository.save(libraryEntity);

		LibraryResponseDto libraryResponseDto = new LibraryResponseDto();
		libraryResponseDto.setLibraryId(libraryEntity.getLibraryId());

		return libraryResponseDto;
	}

	public void deleteLibrary(Long libraryId) {
		libraryRepository.deleteById(libraryId);
	}

}
