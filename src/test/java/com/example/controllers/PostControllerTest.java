package com.example.controllers;

import com.example.model.dto.AddPostRq;
import com.example.model.dto.PostRs;
import com.example.model.entity.Post;
import com.example.support.DataProvider;
import com.example.support.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class PostControllerTest extends IntegrationTestBase {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void beforeEach() {
        restTemplateBuilder = new RestTemplateBuilder()
            .rootUri("http://localhost:" + localPort);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void addPostShouldWork() {
        final AddPostRq request = DataProvider.prepareAddPostRq().build();
        final ResponseEntity<PostRs> entity = testRestTemplate.postForEntity("/api/v1/posts",
            request, PostRs.class);

        assertThat(entity)
            .extracting(HttpEntity::getBody)
            .isEqualTo(PostRs.builder()
                .id(1L)
                .description("desc 1")
                .name("name 1")
                .comments(List.of())
                .build());

        assertThat(postRepository.findById(1L).get())
            .isEqualTo(Post.builder()
                .id(1L)
                .description("desc 1")
                .name("name 1")
                .comments(List.of())
                .build());
        postRepository.deleteById(1L);
    }

    @Test
    void getPostShouldWork() {

        final AddPostRq request = DataProvider.prepareAddPostRq().build();
        testRestTemplate.postForEntity("/api/v1/posts",
            request, PostRs.class);
        final ResponseEntity<PostRs> entity1 = testRestTemplate.getForEntity("/api/v1/posts/{id}", PostRs.class, 1);

        assertThat(entity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity1.getBody())
            .isEqualTo(PostRs.builder()
                .id(1L)
                .description("desc 1")
                .name("name 1")
                .comments(List.of())
                .build());

        postRepository.deleteById(1L);

    }

    @Test
    void deletePostShouldWork() {
        final AddPostRq request = DataProvider.prepareAddPostRq().build();
        testRestTemplate.postForEntity("/api/v1/posts",
            request, PostRs.class);

        testRestTemplate.delete("/api/v1/posts/{id}", 1L);

        assertThat(postRepository.findById(1L)).isEmpty();
    }
}
