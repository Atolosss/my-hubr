package com.example.mapper;

import com.example.model.dto.AddPostRq;
import com.example.model.dto.PostRs;
import com.example.model.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapperConfiguration.class,
    uses = CommentMapper.class
)
public interface PostMapper {
    Post toPost(AddPostRq dto);

    @Mapping(target = "comments", source = "comments", qualifiedByName = "mapCommentRsList")
    PostRs toPostRs(Post post);
}
