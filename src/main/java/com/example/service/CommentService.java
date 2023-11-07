package com.example.service;

import com.example.exceptions.ServiceException;
import com.example.mapper.ChatMapper;
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
    private final ChatMapper chatMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<CommentRs> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(chatMapper::toCommentRs)
                .toList();
    }

    public List<CommentRs> findAll(final CommentSortType sort) {
        return commentRepository.findAll().stream()
                .sorted(sort.getComparator())
                .map(chatMapper::toCommentRs)
                .toList();
    }

    public CommentRs findById(final Long id) {
        return commentRepository.findById(id)
                .map(chatMapper::toCommentRs)
                .orElseThrow(() -> new ServiceException("Comment not found exception with id"));
    }

    public CommentRs save(final AddCommentRq request) {
        final Comment comment = postRepository.findById(request.getPostId())
                .map(post -> chatMapper.toComment(request, post))
                .orElseThrow(() -> new ServiceException("Post not found exception with id"));

        commentRepository.save(comment);

        return chatMapper.toCommentRs(comment);
    }

    public void deleteById(final Long id) {
        commentRepository.deleteById(id);
    }

    public CommentRs update(final Long id, final AddCommentRq request) {
        final Comment comment = postRepository.findById(request.getPostId())
                .map(post -> chatMapper.toComment(request, post))
                .orElseThrow(() -> new ServiceException("Post not found exception with id"));

        comment.setId(id);
        commentRepository.save(comment);

        return chatMapper.toCommentRs(comment);
    }
}
