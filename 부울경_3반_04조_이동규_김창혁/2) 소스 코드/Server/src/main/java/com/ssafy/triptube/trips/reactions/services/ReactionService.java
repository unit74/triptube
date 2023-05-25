package com.ssafy.triptube.trips.reactions.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.ssafy.triptube.trips.attractions.dtos.AttractionInfoDto;
import com.ssafy.triptube.trips.attractions.models.AttractionInfoEntity;
import com.ssafy.triptube.trips.attractions.repositories.AttractionInfoRepository;
import com.ssafy.triptube.trips.reactions.dtos.ReactionResponseDto;
import com.ssafy.triptube.trips.reactions.models.ReactionEntity;
import com.ssafy.triptube.trips.reactions.repositories.ReactionRepository;
import com.ssafy.triptube.users.models.UserEntity;
import com.ssafy.triptube.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReactionService {

	private final ReactionRepository reactionRepository;

	private final AttractionInfoRepository attractionInfoRepository;

	private final UserRepository userRepository;

	public List<ReactionResponseDto> getLikeAttractions(Long userId, Integer page) {
		List<ReactionResponseDto> reactionDtos = new ArrayList<>();

		PageRequest pageRequest = PageRequest.of(page - 1, 12);

		Slice<ReactionEntity> reactionEntitySlice = reactionRepository.findByUser_UserId(userId, pageRequest);

		for (ReactionEntity reactionEntity : reactionEntitySlice.getContent()) {
			ReactionResponseDto reactionDto = new ReactionResponseDto();

			AttractionInfoDto attractionInfoDto = new AttractionInfoDto();

			AttractionInfoEntity attractionInfoEntity = reactionEntity.getAttractionInfo();

			attractionInfoDto.setContentId(attractionInfoEntity.getContentId());
			attractionInfoDto.setTitle(attractionInfoEntity.getTitle());
			attractionInfoDto.setAddr1(attractionInfoEntity.getAddr1());
			attractionInfoDto.setFirstImage(attractionInfoEntity.getFirstImage());
			attractionInfoDto.setFirstImage2(attractionInfoEntity.getFirstImage2());
			attractionInfoDto.setReadcount(attractionInfoEntity.getReadcount());

			reactionDto.setAttractionInfo(attractionInfoDto);
			reactionDto.setCreatedAt(reactionEntity.getCreatedAt());
			reactionDto.setUpdatedAt(reactionEntity.getUpdatedAt());

			reactionDtos.add(reactionDto);
		}

		return reactionDtos;
	}

	public ReactionResponseDto getReaction(Long userId, Integer contentId) {
		ReactionResponseDto reactionDto = new ReactionResponseDto();

		reactionDto.setContentId(contentId);

		ReactionEntity reactionEntity = reactionRepository.findByUser_UserIdAndAttractionInfo_ContentId(userId,
				contentId);

		if (reactionEntity != null) {
			reactionDto.setType(reactionEntity.getType().toString());
		}

		return reactionDto;
	}

	public void react(Long userId, Integer contentId, ReactionEntity.Type type) {
		ReactionEntity reactionEntity = reactionRepository.findByUser_UserIdAndAttractionInfo_ContentId(userId,
				contentId);
		if (reactionEntity == null) {
			reactionEntity = new ReactionEntity();
		}

		AttractionInfoEntity attractionInfoEntity = reactionEntity.getAttractionInfo();
		if (attractionInfoEntity == null) {
			attractionInfoEntity = attractionInfoRepository.findById(contentId).get();
		}

		UserEntity userEntity = reactionEntity.getUser();
		if (userEntity == null) {
			userEntity = userRepository.findById(userId).get();
		}

		reactionEntity.setUser(userEntity);
		reactionEntity.setAttractionInfo(attractionInfoEntity);
		reactionEntity.setType(type);

		reactionRepository.save(reactionEntity);
	}

	@Transactional
	public AttractionInfoDto cancelReaction(Long userId, Integer contentId) {
		reactionRepository.deleteByUser_UserIdAndAttractionInfo_ContentId(userId, contentId);

		AttractionInfoDto attractionInfoDto = new AttractionInfoDto();

		attractionInfoDto.setLikes(
				reactionRepository.countByTypeAndAttractionInfo_ContentId(ReactionEntity.Type.LIKE, contentId));
		attractionInfoDto.setDislikes(
				reactionRepository.countByTypeAndAttractionInfo_ContentId(ReactionEntity.Type.DISLIKE, contentId));

		return attractionInfoDto;
	}

}
