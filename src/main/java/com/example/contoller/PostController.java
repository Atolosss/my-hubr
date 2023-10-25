package com.example.contoller;

import com.example.model.dto.AddPostRq;
import com.example.model.dto.PostRs;
import com.example.model.enums.PostSortType;
import com.example.service.PostService;
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

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostRs> getPosts(@RequestParam(required = false) final PostSortType sort) {
        return postService.getPosts(sort);
    }

    @GetMapping("/{id}")
    public PostRs getPost(@PathVariable final Long id) {

        return postService.getPost(id);
    }

    @PostMapping
    public PostRs addPost(@RequestBody final AddPostRq addPostRq) {
        return postService.save(addPostRq);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable final Long id) {
        postService.deleteById(id);
    }

}
