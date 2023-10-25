package com.example.service;

import com.example.exceptions.ErrorCode;
import com.example.exceptions.ServiceException;
import com.example.mapper.CommentMapper;
import com.example.model.dto.AddCommentRq;
import com.example.model.dto.CommentRs;
import com.example.model.entity.Comment;
import com.example.model.enums.CommentSortType;
import com.example.repository.CommentRepository;
import com.example.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<CommentRs> findAll(final CommentSortType sort) {
        return commentRepository.findAll().stream()
            .sorted(sort.getComparator())
            .map(commentMapper::mapToCommentRs)
            .toList();
    }

    public CommentRs findById(final Long id) {
        return commentRepository.findById(id)
            .map(commentMapper::mapToCommentRs)
            .orElseThrow(() -> new ServiceException(ErrorCode.ERROR_CODE_0002, id));
    }

    public CommentRs save(final AddCommentRq request) {
        Comment comment = postRepository.findById(request.getPostId())
            .map(post -> commentMapper.mapToComment(request, post))
            .orElseThrow(() -> new ServiceException(ErrorCode.ERROR_CODE_0001, request.getPostId()));

        commentRepository.save(comment);

        return commentMapper.mapToCommentRs(comment);
    }

    public void deleteById(final Long id) {
        commentRepository.deleteById(id);
    }
}
