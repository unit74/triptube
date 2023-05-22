package com.ssafy.triptube.trips.attractions.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.triptube.trips.attractions.dtos.AttractionInfoDto;
import com.ssafy.triptube.trips.attractions.models.AttractionInfoEntity;
import com.ssafy.triptube.trips.attractions.repositories.AttractionInfoRepository;
import com.ssafy.triptube.trips.comments.repositories.CommentRepository;
import com.ssafy.triptube.trips.reactions.models.ReactionEntity;
import com.ssafy.triptube.trips.reactions.repositories.ReactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {

	private final AttractionInfoRepository attractionInfoRepository;

	private final ReactionRepository reactionRepository;

	private final CommentRepository commentRepository;

	public List<AttractionInfoDto> getRandomAttractions() {
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
		AttractionInfoEntity attractionInfoEntity = attractionInfoRepository.findById(contentId).get();
		attractionInfoEntity.setReadcount(attractionInfoEntity.getReadcount() + 1);
		attractionInfoEntity = attractionInfoRepository.save(attractionInfoEntity);

		AttractionInfoDto attractionInfoDto = new AttractionInfoDto();

		attractionInfoDto.setContentId(attractionInfoEntity.getContentId());
		attractionInfoDto.setTitle(attractionInfoEntity.getTitle());
		attractionInfoDto.setAddr1(attractionInfoEntity.getAddr1());
		attractionInfoDto.setFirstImage(attractionInfoEntity.getFirstImage());
		attractionInfoDto.setFirstImage2(attractionInfoEntity.getFirstImage2());
		attractionInfoDto.setReadcount(attractionInfoEntity.getReadcount());
		attractionInfoDto.setLatitude(attractionInfoEntity.getLatitude());
		attractionInfoDto.setLongitude(attractionInfoEntity.getLongitude());

		attractionInfoDto.setOverview(attractionInfoEntity.getAttractionDescription().getOverview());

		attractionInfoDto.setLikes(
				reactionRepository.countByTypeAndAttractionInfo_ContentId(ReactionEntity.Type.LIKE, contentId));
		attractionInfoDto.setDislikes(
				reactionRepository.countByTypeAndAttractionInfo_ContentId(ReactionEntity.Type.DISLIKE, contentId));

		attractionInfoDto.setComments(commentRepository.countByContentId(contentId));

		return attractionInfoDto;
	}

	public List<AttractionInfoDto> getSearchAttractions(String searchText, Integer page) {
		List<AttractionInfoDto> attractionInfoDtos = new ArrayList<>();

		PageRequest pageRequest = PageRequest.of(page - 1, 12);
		String title = "%" + searchText + "%";

		Slice<AttractionInfoEntity> attractionInfoEntitySlice = attractionInfoRepository.findByTitleLike(title,
				pageRequest);

		for (AttractionInfoEntity attractionInfoEntity : attractionInfoEntitySlice.getContent()) {
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

	public List<AttractionInfoDto> getHotAttractions(Integer page) {
		List<AttractionInfoDto> attractionInfoDtos = new ArrayList<>();

		PageRequest pageRequest = PageRequest.of(page - 1, 12);

		Slice<AttractionInfoEntity> attractionInfoEntitySlice = attractionInfoRepository
				.findAllByOrderByReadcountDesc(pageRequest);

		for (AttractionInfoEntity attractionInfoEntity : attractionInfoEntitySlice.getContent()) {
			AttractionInfoDto attractionInfoDto = new AttractionInfoDto();

			attractionInfoDto.setContentId(attractionInfoEntity.getContentId());
			attractionInfoDto.setTitle(attractionInfoEntity.getTitle());
			attractionInfoDto.setAddr1(attractionInfoEntity.getAddr1());
			attractionInfoDto.setFirstImage(attractionInfoEntity.getFirstImage());
			attractionInfoDto.setFirstImage2(attractionInfoEntity.getFirstImage2());
			attractionInfoDto.setReadcount(attractionInfoEntity.getReadcount());
			attractionInfoDto.setOverview(attractionInfoEntity.getAttractionDescription().getOverview());

			attractionInfoDtos.add(attractionInfoDto);
		}

		return attractionInfoDtos;
	}

	public List<AttractionInfoDto> getLikeAttractions(Long userId, Integer page) {
		List<AttractionInfoDto> attractionInfoDtos = new ArrayList<>();

		PageRequest pageRequest = PageRequest.of(page - 1, 12);

		Slice<ReactionEntity> reactionEntitySlice = reactionRepository.findByUser_UserId(userId, pageRequest);

		for (ReactionEntity reactionEntity : reactionEntitySlice.getContent()) {
			AttractionInfoDto attractionInfoDto = new AttractionInfoDto();

			AttractionInfoEntity attractionInfoEntity = reactionEntity.getAttractionInfo();

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
}
