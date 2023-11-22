package com.example.support;

import com.example.model.dto.AddCommentRq;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.UpdateCommentRq;
import com.example.model.entity.Comment;
import com.example.model.entity.Post;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class DataProvider {
    public static AddPostRq.AddPostRqBuilder prepareAddPostRq() {
        return AddPostRq.builder()
            .description("desc 1")
            .name("name 1");
    }

    public static AddCommentRq.AddCommentRqBuilder prepareAddCommentRq() {
        return AddCommentRq.builder()
            .comment("Hello");
    }

    public static Post.PostBuilder<?, ?> preparePost(final List<Comment> comments) {
        return Post.builder()
            .name("spring in action")
            .description("spring in action description")
            .comments(comments);
    }

    public static UpdateCommentRq.UpdateCommentRqBuilder prepareUpdateCommentRq() {
        return UpdateCommentRq.builder()
            .comment("Hello1");
    }
}
