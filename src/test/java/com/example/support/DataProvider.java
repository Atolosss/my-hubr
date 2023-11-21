package com.example.support;

import com.example.model.dto.AddCommentRq;
import com.example.model.dto.AddPostRq;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataProvider {
    public static AddPostRq.AddPostRqBuilder prepareAddPostRq() {
        return AddPostRq.builder()
            .description("desc 1")
            .name("name 1");
    }

    public static AddCommentRq.AddCommentRqBuilder prepareAddCommentRq() {
        return AddCommentRq.builder()
            .comment("Hello")
            .postId(1L);
    }
}
