package com.ssafy.triptube.trips.comments.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triptube.trips.comments.dtos.CommentResponseDto;
import com.ssafy.triptube.trips.comments.models.CommentEntity;
import com.ssafy.triptube.trips.comments.repositories.CommentRepository;
import com.ssafy.triptube.trips.replies.dtos.ReplyResponseDto;
import com.ssafy.triptube.trips.replies.models.ReplyEntity;
import com.ssafy.triptube.users.dtos.UserDto;
import com.ssafy.triptube.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	private final UserRepository userRepository;

	public List<CommentResponseDto> getAttractionComments(Integer contentId) {
		List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

		List<CommentEntity> commentEntities = commentRepository.findAllByContentId(contentId);

		for (CommentEntity commentEntity : commentEntities) {
			CommentResponseDto commentResponseDto = new CommentResponseDto();

			commentResponseDto.setCommentId(commentEntity.getCommentId());
			commentResponseDto.setText(commentEntity.getText());
			commentResponseDto.setUser(new UserDto(commentEntity.getUser().getEmail(),
					commentEntity.getUser().getName(), commentEntity.getUser().getProfilePhotoUrl()));
			commentResponseDto.setContentId(commentEntity.getContentId());
			commentResponseDto.setCreatedAt(commentEntity.getCreatedAt());
			commentResponseDto.setUpdatedAt(commentEntity.getUpdatedAt());

			List<ReplyResponseDto> replies = new ArrayList<>();
			for (ReplyEntity replyEntity : commentEntity.getReplies()) {
				ReplyResponseDto replyResponseDto = new ReplyResponseDto();

				replyResponseDto.setReplyId(replyEntity.getReplyId());
				replyResponseDto.setText(replyEntity.getText());
				replyResponseDto.setUser(new UserDto(replyEntity.getUser().getEmail(), replyEntity.getUser().getName(),
						replyEntity.getUser().getProfilePhotoUrl()));
				replyResponseDto.setCommentId(replyEntity.getComment().getCommentId());
				replyResponseDto.setCreatedAt(replyEntity.getCreatedAt());
				replyResponseDto.setUpdatedAt(replyEntity.getUpdatedAt());

				replies.add(replyResponseDto);
			}

			commentResponseDto.setReplies(replies);

			commentResponseDtos.add(commentResponseDto);
		}

		return commentResponseDtos;
	}

	public CommentResponseDto commentAttraction(Long userId, Integer contentId, String text) {
		CommentEntity commentEntity = new CommentEntity();

		commentEntity.setText(text);
		commentEntity.setUser(userRepository.findById(userId).get());
		commentEntity.setContentId(contentId);

		commentEntity = commentRepository.save(commentEntity);

		CommentResponseDto commentDto = new CommentResponseDto();
		commentDto.setCommentId(commentEntity.getCommentId());
		commentDto.setText(commentEntity.getText());
		commentDto.setUser(new UserDto(commentEntity.getUser().getEmail(), commentEntity.getUser().getName(),
				commentEntity.getUser().getProfilePhotoUrl()));
		commentDto.setContentId(commentEntity.getContentId());
		commentDto.setCreatedAt(commentEntity.getCreatedAt());
		commentDto.setUpdatedAt(commentEntity.getUpdatedAt());

		return commentDto;
	}

	public boolean deleteComment(Long userId, Long commentId) {
		CommentEntity commentEntity = commentRepository.findById(commentId).get();

		if (commentEntity.getUser().getUserId() != userId) {
			return false;
		}

		commentRepository.deleteById(commentId);

		return true;
	}
}
