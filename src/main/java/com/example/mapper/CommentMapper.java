package com.example.mapper;

import com.example.model.dto.AddCommentRq;
import com.example.model.dto.CommentRs;
import com.example.model.entity.Comment;
import com.example.model.entity.Post;
import org.springframework.stereotype.Component;

// todo: refactor with https://mapstruct.org/
@Component
public class CommentMapper {
    public Comment mapToComment(final AddCommentRq request, final Post post) {
        return Comment.builder()
            .value(request.getComment())
            .post(post)
            .build();
    }

    public CommentRs mapToCommentRs(final Comment comment) {
        return CommentRs.builder()
            .id(comment.getId())
            .value(comment.getValue())
            .build();
    }

}
