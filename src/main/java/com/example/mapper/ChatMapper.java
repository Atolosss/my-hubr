package com.example.mapper;

import com.example.model.dto.AddCommentRq;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.CommentRs;
import com.example.model.dto.PostRs;
import com.example.model.entity.Comment;
import com.example.model.entity.PostToChat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ChatMapper {

    default Comment toComment(final AddCommentRq dto, final PostToChat post) {
        return Comment.builder()
                .value(dto.getComment())
                .postToChat(post)
                .build();
    }

    @Mapping(target = "value", source = "value")
    CommentRs toCommentRs(Comment entity);

    PostToChat toPost(AddPostRq dto);

    @Mapping(target = "comments", ignore = true)
    PostRs toPostRs(PostToChat entity);

    List<CommentRs> commentRsList(List<Comment> comments);
}
