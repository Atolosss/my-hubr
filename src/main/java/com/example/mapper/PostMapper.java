package com.example.mapper;

import com.example.model.dto.AddPostRq;
import com.example.model.dto.PostRs;
import com.example.model.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface PostMapper {
    Post toPost(AddPostRq dto);

    @Mapping(target = "comments", ignore = true)
    PostRs toPostRs(Post entity);
}