package com.example.mapper;

import com.example.model.dto.AddPostRq;
import com.example.model.dto.PostRs;
import com.example.model.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post mapToPost(final AddPostRq request) {
        return Post.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public PostRs mapToPostRs(final Post post) {
        return PostRs.builder()
                .id(post.getId())
                .name(post.getName())
                .description(post.getDescription())
                .build();
    }

}

