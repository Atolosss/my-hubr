package com.example.mapper;

import com.example.model.dto.AddCommentRq;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.CommentRs;
import com.example.model.dto.PostRs;
import com.example.model.entity.Comment;
import com.example.model.entity.Post;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// todo: разделить маппер на Post и Comment
// todo: @BeanMapping(ignoreByDefault = true)
@Mapper(config = MapperConfiguration.class)
public interface ChatMapper {

    default Comment toComment(final AddCommentRq dto, final Post post) {
        return Comment.builder()
            .value(dto.getComment())
            .post(post)
            .build();
    }

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "value", source = "value")
    @Mapping(target = "id", source = "id")
    CommentRs toCommentRs(Comment entity);

    Post toPost(AddPostRq dto);

    @Mapping(target = "comments", ignore = true)
    PostRs toPostRs(Post entity);

    List<CommentRs> commentRsList(List<Comment> comments);
}
