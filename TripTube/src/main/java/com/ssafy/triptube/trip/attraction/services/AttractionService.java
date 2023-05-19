package com.ssafy.triptube.trip.attraction.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.triptube.trip.attraction.dtos.AttractionInfoDto;
import com.ssafy.triptube.trip.attraction.models.AttractionDescriptionEntity;
import com.ssafy.triptube.trip.attraction.models.AttractionInfoEntity;
import com.ssafy.triptube.trip.attraction.repositories.AttractionDescriptionRepository;
import com.ssafy.triptube.trip.attraction.repositories.AttractionInfoRepository;
import com.ssafy.triptube.trip.reaction.models.ReactionEntity;
import com.ssafy.triptube.trip.reaction.repositories.ReactionRepository;

@Service
public class AttractionService {

	private final AttractionInfoRepository attractionInfoRepository;

	private final AttractionDescriptionRepository attractionDescriptionRepository;

	private final ReactionRepository reactionRepository;

	@Autowired
	public AttractionService(AttractionInfoRepository attractionInfoRepository,
			AttractionDescriptionRepository attractionDescriptionRepository, ReactionRepository reactionRepository) {
		this.attractionInfoRepository = attractionInfoRepository;
		this.attractionDescriptionRepository = attractionDescriptionRepository;
		this.reactionRepository = reactionRepository;
	}

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

	@Transactional
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
