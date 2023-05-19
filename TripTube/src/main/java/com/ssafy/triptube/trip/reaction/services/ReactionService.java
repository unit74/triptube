package com.ssafy.triptube.trip.reaction.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.triptube.trip.attraction.models.AttractionInfoEntity;
import com.ssafy.triptube.trip.attraction.repositories.AttractionInfoRepository;
import com.ssafy.triptube.trip.reaction.models.ReactionEntity;
import com.ssafy.triptube.trip.reaction.repositories.ReactionRepository;
import com.ssafy.triptube.user.models.UserEntity;
import com.ssafy.triptube.user.repositories.UserRepository;

@Service
public class ReactionService {

	private final ReactionRepository reactionRepository;

	private final AttractionInfoRepository AttractionInfoRepository;

	private final UserRepository userRepository;

	@Autowired
	public ReactionService(ReactionRepository reactionRepository,
			com.ssafy.triptube.trip.attraction.repositories.AttractionInfoRepository attractionInfoRepository,
			UserRepository userRepository) {
		this.reactionRepository = reactionRepository;
		AttractionInfoRepository = attractionInfoRepository;
		this.userRepository = userRepository;
	}

	public ReactionEntity react(Long userId, Integer contentId, ReactionEntity.Type type) {
		ReactionEntity reactionEntity = new ReactionEntity();

		UserEntity userEntity = userRepository.findById(userId).get();
		System.out.println(userEntity);

		AttractionInfoEntity attractionInfoEntity = AttractionInfoRepository.findById(contentId).get();
		System.out.println(attractionInfoEntity);

		reactionEntity.setUser(userEntity);
		reactionEntity.setAttractionInfo(attractionInfoEntity);
		reactionEntity.setType(type);

		return reactionRepository.save(reactionEntity);
	}

}
