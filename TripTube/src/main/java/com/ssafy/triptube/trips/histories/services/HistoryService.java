package com.ssafy.triptube.trips.histories.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trips.attractions.dtos.AttractionInfoDto;
import com.ssafy.triptube.trips.attractions.repositories.AttractionInfoRepository;
import com.ssafy.triptube.trips.histories.dtos.HistoryDto;
import com.ssafy.triptube.trips.histories.models.HistoryEntity;
import com.ssafy.triptube.trips.histories.repositories.HistoryRepository;
import com.ssafy.triptube.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {

	private final HistoryRepository historyRepository;

	private final UserRepository userRepository;

	private final AttractionInfoRepository attractionInfoRepository;

	public void visitAttraction(Long userId, Integer contentId) {
		HistoryEntity historyEntity = historyRepository.findByUser_UserIdAndAttractionInfo_ContentId(userId, contentId);

		if (historyEntity == null) {
			historyEntity = new HistoryEntity();

			historyEntity.setType(HistoryEntity.Type.VISIT);
			historyEntity.setUser(userRepository.findById(userId).get());
			historyEntity.setAttractionInfo(attractionInfoRepository.findById(contentId).get());
		}

		historyEntity.setUpdatedAt(null);
		historyRepository.save(historyEntity);
	}

	public void searchAttraction(Long userId, String searchText) {
		HistoryEntity historyEntity = historyRepository.findByUser_UserIdAndSearchText(userId, searchText);

		if (historyEntity == null) {
			historyEntity = new HistoryEntity();

			historyEntity.setSearchText(searchText);
			historyEntity.setType(HistoryEntity.Type.SEARCH);
			historyEntity.setUser(userRepository.findById(userId).get());
		}

		historyEntity.setUpdatedAt(null);
		historyRepository.save(historyEntity);
	}

	public List<HistoryDto> getHistories(Long userId, HistoryEntity.Type type) {
		List<HistoryDto> historyDtos = new ArrayList<>();

		List<HistoryEntity> HistoryEntities = historyRepository.findAllByUser_UserIdAndTypeOrderByUpdatedAtDesc(userId,
				type);

		for (HistoryEntity historyEntity : HistoryEntities) {
			HistoryDto historyDto = new HistoryDto();

			historyDto.setHistoryId(historyEntity.getHistoryId());
			historyDto.setSearchText(historyEntity.getSearchText());

			AttractionInfoDto attractionInfoDto = new AttractionInfoDto();
			attractionInfoDto.setContentId(historyEntity.getAttractionInfo().getContentId());
			attractionInfoDto.setTitle(historyEntity.getAttractionInfo().getTitle());
			attractionInfoDto.setAddr1(historyEntity.getAttractionInfo().getAddr1());
			attractionInfoDto.setFirstImage(historyEntity.getAttractionInfo().getFirstImage());
			attractionInfoDto.setFirstImage2(historyEntity.getAttractionInfo().getFirstImage2());
			attractionInfoDto.setReadcount(historyEntity.getAttractionInfo().getReadcount());
			historyDto.setAttractionInfo(attractionInfoDto);

			historyDto.setCreatedAt(historyEntity.getCreatedAt());
			historyDto.setUpdatedAt(historyEntity.getUpdatedAt());

			historyDtos.add(historyDto);
		}

		return historyDtos;
	}

	public void deleteHistory(Long historyId) {
		historyRepository.deleteById(historyId);
	}
}
