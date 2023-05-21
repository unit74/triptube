package com.ssafy.triptube.trips.attractions.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trips.attractions.dtos.AttractionInfoDto;
import com.ssafy.triptube.trips.attractions.models.AttractionDescriptionEntity;
import com.ssafy.triptube.trips.attractions.models.AttractionInfoEntity;
import com.ssafy.triptube.trips.attractions.repositories.AttractionDescriptionRepository;
import com.ssafy.triptube.trips.attractions.repositories.AttractionInfoRepository;
import com.ssafy.triptube.trips.reactions.models.ReactionEntity;
import com.ssafy.triptube.trips.reactions.repositories.ReactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {

	private final AttractionInfoRepository attractionInfoRepository;

	private final AttractionDescriptionRepository attractionDescriptionRepository;

	private final ReactionRepository reactionRepository;

	public List<AttractionInfoDto> findRandomAttractionInfos() {
		List<AttractionInfoEntity> attractionInfoEntities = attractionInfoRepository.findRandomAttractionInfos();

		List<AttractionInfoDto> attractionInfoDtos = new ArrayList<>();

		for (AttractionInfoEntity attractionInfoEntity : attractionInfoEntities) {
			AttractionInfoDto attractionInfoDto = new AttractionInfoDto();

			attractionInfoDto.setContentId(attractionInfoEntity.getContentId());
			attractionInfoDto.setTitle(attractionInfoEntity.getTitle());
			attractionInfoDto.setAddr1(attractionInfoEntity.getAddr1());
			attractionInfoDto.setFirstImage(attractionInfoEntity.getFirstImage());
			attractionInfoDto.setFirstImage2(attractionInfoEntity.getFirstImage2());
			attractionInfoDto.setReadcount(attractionInfoEntity.getReadcount());

			attractionInfoDtos.add(attractionInfoDto);
		}

		return attractionInfoDtos;
	}

	public AttractionInfoDto getAttractionDetail(Integer contentId) {
		AttractionInfoDto attractionInfoDto = new AttractionInfoDto();

		AttractionInfoEntity attractionInfoEntity = attractionInfoRepository.findById(contentId).get();
		attractionInfoDto.setContentId(attractionInfoEntity.getContentId());
		attractionInfoDto.setTitle(attractionInfoEntity.getTitle());
		attractionInfoDto.setAddr1(attractionInfoEntity.getAddr1());
		attractionInfoDto.setFirstImage(attractionInfoEntity.getFirstImage());
		attractionInfoDto.setFirstImage2(attractionInfoEntity.getFirstImage2());
		attractionInfoDto.setReadcount(attractionInfoEntity.getReadcount());
		attractionInfoDto.setLatitude(attractionInfoEntity.getLatitude());
		attractionInfoDto.setLongitude(attractionInfoEntity.getLongitude());

		AttractionDescriptionEntity attrationDescriptionEntity = attractionDescriptionRepository.findById(contentId)
				.get();
		attractionInfoDto.setOverview(attrationDescriptionEntity.getOverview());

		attractionInfoDto.setLikes(
				reactionRepository.countByTypeAndAttractionInfo_ContentId(ReactionEntity.Type.LIKE, contentId));
		attractionInfoDto.setDislikes(
				reactionRepository.countByTypeAndAttractionInfo_ContentId(ReactionEntity.Type.DISLIKE, contentId));

		attractionInfoDto.setComments(1L);

		return attractionInfoDto;
	}
}
