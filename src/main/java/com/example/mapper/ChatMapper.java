package com.example.mapper;

import com.example.model.dto.AddCommentRq;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.CommentRs;
import com.example.model.dto.PostRs;
import com.example.model.entity.Comment;
import com.example.model.entity.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    Comment toComment(AddCommentRq dto, Post post);

    CommentRs toCommentRq(Comment entity);

    List<CommentRs> commentRsList(List<Comment> comments);

    Post toPost(AddPostRq dto);

    PostRs toPostRs(Post entity);

    List<PostRs> postRsList(List<Post> posts);

    List<Post> postList(List<PostRs> postRsList);
}
