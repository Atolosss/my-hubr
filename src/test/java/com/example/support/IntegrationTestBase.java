package com.example.support;

import com.example.model.entity.Post;
import com.example.repository.CommentRepository;
import com.example.repository.PostRepository;
import com.example.service.CommentService;
import com.example.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Set;

public class IntegrationTestBase extends DatabaseAwareTestBase {
    @LocalServerPort
    protected int localPort;
    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected PostService postService;
    @Autowired
    protected CommentService commentService;

    @BeforeEach
    void beforeEach() {
        webTestClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:" + localPort)
            .build();
    }

    @Override
    protected String getSchema() {
        return "public";
    }

    @Override
    protected Set<String> getTables() {
        return Set.of("comment", "post");
    }

    protected void createPost(final Post post) {
        postRepository.save(post);
    }

}
