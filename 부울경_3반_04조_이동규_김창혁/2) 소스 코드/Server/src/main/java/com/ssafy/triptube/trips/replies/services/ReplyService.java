package com.ssafy.triptube.trips.replies.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.triptube.trips.comments.repositories.CommentRepository;
import com.ssafy.triptube.trips.replies.dtos.ReplyResponseDto;
import com.ssafy.triptube.trips.replies.models.ReplyEntity;
import com.ssafy.triptube.trips.replies.repositories.ReplyRepository;
import com.ssafy.triptube.users.dtos.UserDto;
import com.ssafy.triptube.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {

	private final ReplyRepository replyRepository;

	private final UserRepository userRepository;

	private final CommentRepository commentRepository;

	public ReplyResponseDto replyComment(Long userId, Long commentId, String text) {
		ReplyEntity replyEntity = new ReplyEntity();

		replyEntity.setText(text);
		replyEntity.setUser(userRepository.findById(userId).get());
		replyEntity.setComment(commentRepository.findById(commentId).get());

		replyEntity = replyRepository.save(replyEntity);

		ReplyResponseDto replyDto = new ReplyResponseDto();
		replyDto.setReplyId(replyEntity.getReplyId());
		replyDto.setText(replyEntity.getText());
		replyDto.setUser(new UserDto(replyEntity.getUser().getEmail(), replyEntity.getUser().getName(),
				replyEntity.getUser().getProfilePhotoUrl()));
		replyDto.setCommentId(replyEntity.getComment().getCommentId());
		replyDto.setCreatedAt(replyEntity.getCreatedAt());
		replyDto.setUpdatedAt(replyEntity.getUpdatedAt());

		return replyDto;
	}

	@Transactional
	public boolean deleteReply(Long userId, Long replyId) {
		ReplyEntity replyEntity = replyRepository.findById(replyId).get();

		if (replyEntity.getUser().getUserId() != userId) {
			return false;
		}

		replyRepository.deleteById(replyId);

		return true;
	}

	public List<ReplyResponseDto> getReplies(Long userId) {
		List<ReplyResponseDto> replyResponseDtos = new ArrayList<>();

		List<ReplyEntity> replyEntities = replyRepository.findAllByUser_UserId(userId);

		for (ReplyEntity replyEntity : replyEntities) {
			ReplyResponseDto replyResponseDto = new ReplyResponseDto();

			replyResponseDto.setReplyId(replyEntity.getReplyId());
			replyResponseDto.setText(replyEntity.getText());
			replyResponseDto.setContentId(replyEntity.getComment().getContentId());
			replyResponseDto.setCreatedAt(replyEntity.getCreatedAt());
			replyResponseDto.setUpdatedAt(replyEntity.getUpdatedAt());

			replyResponseDtos.add(replyResponseDto);
		}

		return replyResponseDtos;
	}

	@Transactional
	public ReplyResponseDto updateReply(Long userId, Long replyId, String text) {
		ReplyEntity replyEntity = replyRepository.findById(replyId).get();

		if (replyEntity.getUser().getUserId() != userId) {
			return null;
		}

		if (text == null) {
			return null;
		}

		replyEntity.setText(text);
		replyEntity.setUpdatedAt(null);

		replyEntity = replyRepository.save(replyEntity);

		ReplyResponseDto replyResponseDto = new ReplyResponseDto();

		replyResponseDto.setReplyId(replyEntity.getReplyId());
		replyResponseDto.setText(replyEntity.getText());
		replyResponseDto.setContentId(replyEntity.getComment().getContentId());
		replyResponseDto.setCreatedAt(replyEntity.getCreatedAt());
		replyResponseDto.setUpdatedAt(replyEntity.getUpdatedAt());

		return replyResponseDto;
	}
}
