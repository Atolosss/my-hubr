package com.example.service;

import com.example.constant.ErrorCode;
import com.example.exceptions.ServiceException;
import com.example.mapper.ChatMapper;
import com.example.model.dto.AddCommentRq;
import com.example.model.dto.CommentRs;
import com.example.model.dto.UpdateCommentRq;
import com.example.model.entity.Comment;
import com.example.model.enums.CommentSortType;
import com.example.repository.CommentRepository;
import com.example.repository.PostRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ChatMapper chatMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<CommentRs> findAll(@Nullable final CommentSortType sort) {
        final Comparator<Comment> comparator = sort != null ? sort.getComparator() : CommentSortType.ID.getComparator();
        return commentRepository.findAll().stream()
            .sorted(comparator)
            .map(chatMapper::toCommentRs)
            .toList();
    }

    public CommentRs findById(final Long id) {
        return commentRepository.findById(id)
            .map(chatMapper::toCommentRs)
            .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, id));
    }

    public CommentRs save(final AddCommentRq request) {
        final Comment comment = postRepository.findById(request.getPostId())
            .map(post -> chatMapper.toComment(request, post))
            .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_002, request.getPostId()));

        commentRepository.save(comment);

        return chatMapper.toCommentRs(comment);
    }

    public void deleteById(final Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public CommentRs update(final UpdateCommentRq request) {
        Comment comment = commentRepository.findById(request.getId())
            .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, request.getId()));

        // todo: move to mapper
        comment.setValue(request.getComment());

        return chatMapper.toCommentRs(comment);
    }
}
