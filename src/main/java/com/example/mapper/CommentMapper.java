package com.example.mapper;

import com.example.model.dto.AddCommentRq;
import com.example.model.dto.CommentRs;
import com.example.model.dto.UpdateCommentRq;
import com.example.model.entity.Comment;
import com.example.model.entity.Post;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface CommentMapper {
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

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "value", source = "comment")
    void update(@MappingTarget Comment comment, UpdateCommentRq request);

    @Named("mapCommentRsList")
    List<CommentRs> mapCommentRsList(List<Comment> comments);
}
