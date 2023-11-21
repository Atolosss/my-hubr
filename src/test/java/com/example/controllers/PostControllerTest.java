package com.example.controllers;

import com.example.model.dto.AddPostRq;
import com.example.model.dto.PostRs;
import com.example.model.entity.Post;
import com.example.support.DataProvider;
import com.example.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class PostControllerTest extends IntegrationTestBase {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void addPostShouldWork() {
        final AddPostRq request = DataProvider.prepareAddPostRq().build();

        webTestClient.post().uri("/api/v1/posts")
            .body(Mono.just(request), AddPostRq.class)
            .exchange()
            .expectStatus().isOk()
            .expectBody(PostRs.class)
            .value(postRs -> assertThat(postRs).isEqualTo(PostRs.builder()
                .id(1L)
                .description("desc 1")
                .name("name 1")
                .comments(List.of())
                .build()));

        assertThat(postRepository.findById(1L).get())
            .isEqualTo(Post.builder()
                .id(1L)
                .description("desc 1")
                .name("name 1")
                .comments(List.of())
                .build());
    }

    @Test
    void getPostShouldWork() {
        final AddPostRq request = DataProvider.prepareAddPostRq().build();

        webTestClient.post().uri("/api/v1/posts")
            .body(Mono.just(request), AddPostRq.class)
            .exchange()
            .expectStatus().isOk();

        webTestClient.get().uri("/api/v1/posts/{id}", 1L)
            .exchange()
            .expectStatus().isOk()
            .expectBody(PostRs.class)
            .value(postRs -> assertThat(postRs).isEqualTo(PostRs.builder()
                .id(1L)
                .description("desc 1")
                .name("name 1")
                .comments(List.of())
                .build()));
    }

    @Test
    void getPostsShouldWork() {
        final AddPostRq request = DataProvider.prepareAddPostRq().build();

        webTestClient.post().uri("/api/v1/posts")
            .body(Mono.just(request), AddPostRq.class)
            .exchange()
            .expectStatus().isOk();

        webTestClient.get().uri("/api/v1/posts")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(PostRs.class)
            .value(posts -> assertThat(posts).hasSize(1));
    }

    @Test
    void deletePostShouldWork() {
        final AddPostRq request = DataProvider.prepareAddPostRq().build();

        webTestClient.post().uri("/api/v1/posts")
            .body(Mono.just(request), AddPostRq.class)
            .exchange()
            .expectStatus().isOk();

        webTestClient.delete().uri("/api/v1/posts/{id}", 1L)
            .exchange()
            .expectStatus().isOk();

        assertThat(postRepository.findById(1L)).isEmpty();
    }
}
