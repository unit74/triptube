package com.ssafy.triptube.trips.reactions.services;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.configures.security.utils.SecurityUtil;
import com.ssafy.triptube.trips.attractions.models.AttractionInfoEntity;
import com.ssafy.triptube.trips.attractions.repositories.AttractionInfoRepository;
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

	public ReactionEntity react(Integer contentId, ReactionEntity.Type type) {
		ReactionEntity reactionEntity = new ReactionEntity();

		UserEntity userEntity = userRepository.findByEmail(SecurityUtil.getLoginUsername());
		System.out.println(userEntity);

		AttractionInfoEntity attractionInfoEntity = attractionInfoRepository.findById(contentId).get();
		System.out.println(attractionInfoEntity);

		reactionEntity.setUser(userEntity);
		reactionEntity.setAttractionInfo(attractionInfoEntity);
		reactionEntity.setType(type);

		return reactionRepository.save(reactionEntity);
	}

}
