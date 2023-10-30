package com.example.mapper;

import com.example.model.dto.AddCommentRq;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.CommentRs;
import com.example.model.dto.PostRs;
import com.example.model.entity.Comment;
import com.example.model.entity.PostToChat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ChatMapper {
    Comment toComment(AddCommentRq dto, PostToChat postToChat);
    @Mapping(target = "value", source = "comment")
    @Mapping(target = "id", source = "postToChat.id")
    CommentRs toCommentRq(Comment entity);
    PostToChat toPost(AddPostRq dto);

    PostRs toPostRs(PostToChat entity);


}
