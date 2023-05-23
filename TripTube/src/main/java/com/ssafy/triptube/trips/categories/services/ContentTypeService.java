package com.ssafy.triptube.trips.categories.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trips.categories.models.ContentTypeEntity;
import com.ssafy.triptube.trips.categories.repositories.ContentTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentTypeService {

	private final ContentTypeRepository contentTypeRepository;

	public List<ContentTypeEntity> getContentTypes() {
		return contentTypeRepository.findAll();
	}
}
