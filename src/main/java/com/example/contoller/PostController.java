package com.example.contoller;

import com.example.model.entity.Post;
import com.example.model.enums.PostSortType;
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
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    public final PostRepository postRepository;

    @GetMapping
    public List<Post> getPosts(@RequestParam(required = false) final PostSortType sort) {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Post> getPost(@PathVariable final Long id) {
        return postRepository.findById(id);
    }

    @PostMapping
    public Post addPost(@RequestBody final NewPostRequest newPostRequest) {
        return postRepository.save(Post.builder()
                .name(newPostRequest.name())
                .description(newPostRequest.description())
                .build());
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable final Long id) {
        postRepository.deleteById(id);
    }

    record NewPostRequest(String name, String description) {

    }
}
