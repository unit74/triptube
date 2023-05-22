package com.ssafy.triptube.trips.reactions.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trips.attractions.dtos.AttractionInfoDto;
import com.ssafy.triptube.trips.attractions.models.AttractionInfoEntity;
import com.ssafy.triptube.trips.attractions.repositories.AttractionInfoRepository;
import com.ssafy.triptube.trips.reactions.dtos.ReactionDto;
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

	public ReactionDto getReaction(Long userId, Integer contentId) {
		ReactionDto reactionDto = new ReactionDto();

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
