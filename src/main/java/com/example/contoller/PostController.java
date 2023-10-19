package com.example.contoller;

import com.example.model.entity.Post;
import com.example.model.dto.RequestCreatePost;
import com.example.model.enums.PostSortType;
import com.example.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    @Qualifier("postServiceCustomImpl")
    private final PostService postService;

    @GetMapping
    public List<Post> getPosts(@RequestParam(required = false) PostSortType sort) {
        return postService.findAll(sort);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable final Long id) {
        return postService.findById(id);
    }

    @PostMapping
    public Post postPost(@RequestBody RequestCreatePost requestCreatePost) {
        return postService.save(requestCreatePost);
    }
}
