package com.ssafy.triptube.trips.replies.services;

import org.springframework.stereotype.Service;

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

	public boolean deleteReply(Long userId, Long replyId) {
		ReplyEntity replyEntity = replyRepository.findById(replyId).get();

		if (replyEntity.getUser().getUserId() != userId) {
			return false;
		}

		replyRepository.deleteById(replyId);

		return true;
	}
}
