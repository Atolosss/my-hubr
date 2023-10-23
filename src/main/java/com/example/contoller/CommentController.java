package com.example.contoller;

import com.example.model.entity.Comment;
import com.example.model.enums.SortType;
import com.example.repository.CommentRepository;
import com.example.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    public final CommentRepository commentRepository;

    public final PostRepository postRepository;

    @GetMapping
    public List<Comment> getComments(@RequestParam(required = false) final SortType sort) {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Comment> getComment(@PathVariable final Long id) {
        return commentRepository.findById(id);
    }

    @PostMapping
    public void addComment(@RequestBody final NewCommentRequest request) {
        commentRepository.save(Comment.builder().comment(request.name()).post(postRepository.findById(request.postId()).orElseThrow()).build());
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable final Long id) {
        commentRepository.deleteById(id);
    }

    record NewCommentRequest(String name, Long postId) {

    }
}
