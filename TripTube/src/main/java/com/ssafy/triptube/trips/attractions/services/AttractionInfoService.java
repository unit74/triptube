package com.ssafy.triptube.trips.attractions.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.triptube.trips.attractions.dtos.AttractionInfoDto;
import com.ssafy.triptube.trips.attractions.models.AttractionInfoEntity;
import com.ssafy.triptube.trips.attractions.models.QAttractionInfoEntity;
import com.ssafy.triptube.trips.attractions.repositories.AttractionInfoRepository;
import com.ssafy.triptube.trips.comments.repositories.CommentRepository;
import com.ssafy.triptube.trips.reactions.models.QReactionEntity;
import com.ssafy.triptube.trips.reactions.models.ReactionEntity;
import com.ssafy.triptube.trips.reactions.repositories.ReactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionInfoService {

	private final AttractionInfoRepository attractionInfoRepository;

	private final ReactionRepository reactionRepository;

	private final CommentRepository commentRepository;

	private final JPAQueryFactory queryFactory;

	private final EntityManager entityManager;

	private final QAttractionInfoEntity attraction = QAttractionInfoEntity.attractionInfoEntity;

	private final QReactionEntity reaction = QReactionEntity.reactionEntity;

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

	public List<AttractionInfoDto> getSearchAttractions(String searchText, Integer gugun, Integer sido,
			Integer contentType, Integer page) {
		BooleanBuilder builder = new BooleanBuilder();

		builder.and(attraction.title.contains(searchText));

		if (gugun != null) {
			builder.and(attraction.gugunCode.eq(gugun));
		}

		if (sido != null) {
			builder.and(attraction.sidoCode.eq(sido));
		}

		if (contentType != null) {
			builder.and(attraction.contentTypeId.eq(contentType));
		}

		List<AttractionInfoEntity> attractionInfoEntities = queryFactory.selectFrom(attraction).where(builder)
				.offset((page - 1) * 12).limit(12).fetch();

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

	public List<AttractionInfoDto> getTrendingAttractions(Integer page) {
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

	@Transactional(readOnly = true)
	public List<AttractionInfoDto> getTop10LikedAttractions(Integer contentType) {
		String sql = "SELECT * FROM attraction_info ai inner join (SELECT ai.content_id, COUNT(ai.content_id) likes FROM attraction_info ai INNER JOIN reactions r ON ai.content_id = r.content_id WHERE content_type_id = :contentType GROUP BY ai.content_id) r on ai.content_id = r.content_id ORDER BY r.likes DESC LIMIT 10";
		Query nativeQuery = entityManager.createNativeQuery(sql, AttractionInfoEntity.class);
		nativeQuery.setParameter("contentType", contentType);
		List<AttractionInfoEntity> results = nativeQuery.getResultList();

		List<AttractionInfoDto> attractionInfoDtos = new ArrayList<>();
		for (AttractionInfoEntity attractionInfoEntity : results) {
			AttractionInfoDto attractionInfoDto = new AttractionInfoDto();

			attractionInfoDto.setContentId(attractionInfoEntity.getContentId());
			attractionInfoDto.setTitle(attractionInfoEntity.getTitle());
			attractionInfoDto.setAddr1(attractionInfoEntity.getAddr1());
			attractionInfoDto.setFirstImage(attractionInfoEntity.getFirstImage());
			attractionInfoDto.setFirstImage2(attractionInfoEntity.getFirstImage2());
			attractionInfoDto.setReadcount(attractionInfoEntity.getReadcount());
			attractionInfoDto.setLatitude(attractionInfoEntity.getLatitude());
			attractionInfoDto.setLongitude(attractionInfoEntity.getLongitude());

			attractionInfoDtos.add(attractionInfoDto);
		}

		return attractionInfoDtos;
	}

	public List<AttractionInfoDto> getNearbyAttractions(Integer contentId, Integer page) {
		AttractionInfoEntity attractionInfo = attractionInfoRepository.findById(contentId).get();

		String sql = "SELECT *, ST_Distance_Sphere(point(longitude, latitude), point(:longitude, :latitude)) as distance FROM attraction_info ORDER BY distance LIMIT :limit OFFSET :offset";
		Query nativeQuery = entityManager.createNativeQuery(sql, AttractionInfoEntity.class);
		nativeQuery.setParameter("longitude", attractionInfo.getLongitude());
		nativeQuery.setParameter("latitude", attractionInfo.getLatitude());
		nativeQuery.setParameter("limit", 12);
		nativeQuery.setParameter("offset", (page - 1) * 12);
		List<AttractionInfoEntity> results = nativeQuery.getResultList();

		List<AttractionInfoDto> attractionInfoDtos = new ArrayList<>();
		for (AttractionInfoEntity attractionInfoEntity : results) {
			AttractionInfoDto attractionInfoDto = new AttractionInfoDto();

			attractionInfoDto.setContentId(attractionInfoEntity.getContentId());
			attractionInfoDto.setTitle(attractionInfoEntity.getTitle());
			attractionInfoDto.setAddr1(attractionInfoEntity.getAddr1());
			attractionInfoDto.setFirstImage(attractionInfoEntity.getFirstImage());
			attractionInfoDto.setFirstImage2(attractionInfoEntity.getFirstImage2());
			attractionInfoDto.setReadcount(attractionInfoEntity.getReadcount());
			attractionInfoDto.setLatitude(attractionInfoEntity.getLatitude());
			attractionInfoDto.setLongitude(attractionInfoEntity.getLongitude());

			attractionInfoDtos.add(attractionInfoDto);
		}

		return attractionInfoDtos;
	}

}
