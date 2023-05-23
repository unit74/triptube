package com.ssafy.triptube.trips.histories.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public List<HistoryDto> getHistories(Long userId, HistoryEntity.Type type, Integer page) {
		List<HistoryDto> historyDtos = new ArrayList<>();

		PageRequest pageRequest = PageRequest.of(page - 1, 12);

		Slice<HistoryEntity> historyEntitySlice = historyRepository.findByUser_UserIdAndTypeOrderByUpdatedAtDesc(userId,
				type, pageRequest);

		for (HistoryEntity historyEntity : historyEntitySlice.getContent()) {
			HistoryDto historyDto = new HistoryDto();

			historyDto.setHistoryId(historyEntity.getHistoryId());
			historyDto.setSearchText(historyEntity.getSearchText());

			if (type.equals(HistoryEntity.Type.VISIT)) {
				AttractionInfoDto attractionInfoDto = new AttractionInfoDto();
				attractionInfoDto.setContentId(historyEntity.getAttractionInfo().getContentId());
				attractionInfoDto.setTitle(historyEntity.getAttractionInfo().getTitle());
				attractionInfoDto.setAddr1(historyEntity.getAttractionInfo().getAddr1());
				attractionInfoDto.setFirstImage(historyEntity.getAttractionInfo().getFirstImage());
				attractionInfoDto.setFirstImage2(historyEntity.getAttractionInfo().getFirstImage2());
				attractionInfoDto.setReadcount(historyEntity.getAttractionInfo().getReadcount());
				historyDto.setAttractionInfo(attractionInfoDto);
			}

			historyDto.setCreatedAt(historyEntity.getCreatedAt());
			historyDto.setUpdatedAt(historyEntity.getUpdatedAt());

			historyDtos.add(historyDto);
		}

		return historyDtos;
	}

	public void deleteHistory(Long historyId) {
		historyRepository.deleteById(historyId);
	}

	@Transactional
	public void deleteHistories(Long userId, HistoryEntity.Type type) {
		historyRepository.deleteAllByUser_UserIdAndType(userId, type);
	}
}
