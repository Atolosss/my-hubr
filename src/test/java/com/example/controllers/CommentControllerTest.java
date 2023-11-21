package com.example.controllers;

import com.example.model.dto.AddCommentRq;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.CommentRs;
import com.example.model.dto.PostRs;
import com.example.model.dto.UpdateCommentRq;
import com.example.model.entity.Comment;
import com.example.support.DataProvider;
import com.example.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class CommentControllerTest extends IntegrationTestBase {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void addCommentShouldWork() {
        final AddPostRq request1 = DataProvider.prepareAddPostRq().build();
        final PostRs postRs = webTestClient.post().uri("/api/v1/posts")
            .body(Mono.just(request1), AddPostRq.class)
            .exchange()
            .expectStatus().isOk()
            .returnResult(PostRs.class)
            .getResponseBody()
            .blockFirst();

        final AddCommentRq request2 = DataProvider.prepareAddCommentRq().build();
        final CommentRs commentRs = webTestClient.post().uri("/api/v1/comments")
            .body(Mono.just(request2), AddCommentRq.class)
            .exchange()
            .expectStatus().isOk()
            .returnResult(CommentRs.class)
            .getResponseBody()
            .blockFirst();

        assertThat(commentRs)
            .isEqualTo(CommentRs.builder()
                .id(1L)
                .value("Hello")
                .build());

        assertThat(commentRepository.findById(1L).get())
            .isEqualTo(Comment.builder()
                .id(1L)
                .value("Hello")
                .post(postRepository.findById(postRs.getId()).get())
                .build());
    }

    @Test
    void getCommentShouldWork() {
        final AddPostRq request1 = DataProvider.prepareAddPostRq().build();
        webTestClient.post().uri("/api/v1/posts")
            .body(Mono.just(request1), AddPostRq.class)
            .exchange()
            .expectStatus().isOk()
            .returnResult(PostRs.class)
            .getResponseBody()
            .blockFirst();

        final AddCommentRq request2 = DataProvider.prepareAddCommentRq().build();
        webTestClient.post().uri("/api/v1/comments")
            .body(Mono.just(request2), AddCommentRq.class)
            .exchange()
            .expectStatus().isOk()
            .returnResult(CommentRs.class)
            .getResponseBody()
            .blockFirst();

        final CommentRs commentRs = webTestClient.get().uri("/api/v1/comments/{id}", 1L)
            .exchange()
            .expectStatus().isOk()
            .returnResult(CommentRs.class)
            .getResponseBody()
            .blockFirst();

        assertThat(commentRs)
            .isEqualTo(CommentRs.builder()
                .id(1L)
                .value("Hello")
                .build());
    }

    @Test
    void getCommentsShouldWork() {
        final AddPostRq request1 = DataProvider.prepareAddPostRq().build();
        webTestClient.post().uri("/api/v1/posts")
            .body(Mono.just(request1), AddPostRq.class)
            .exchange()
            .expectStatus().isOk()
            .returnResult(PostRs.class)
            .getResponseBody()
            .blockFirst();

        final AddCommentRq request2 = DataProvider.prepareAddCommentRq().build();
        webTestClient.post().uri("/api/v1/comments")
            .body(Mono.just(request2), AddCommentRq.class)
            .exchange()
            .expectStatus().isOk()
            .returnResult(CommentRs.class)
            .getResponseBody()
            .blockFirst();

        final ParameterizedTypeReference<List<CommentRs>> responseType =
            new ParameterizedTypeReference<List<CommentRs>>() {
            };

        final List<CommentRs> comments = webTestClient.get().uri("/api/v1/comments")
            .exchange()
            .expectStatus().isOk()
            .expectBody(responseType)
            .returnResult()
            .getResponseBody();

        assertThat(comments).hasSize(1);
        assertThat(comments.get(0))
            .isEqualTo(CommentRs.builder()
                .id(1L)
                .value("Hello")
                .build());
    }

    @Test
    void deleteCommentShouldWork() {
        final AddPostRq request1 = DataProvider.prepareAddPostRq().build();
        webTestClient.post().uri("/api/v1/posts")
            .body(Mono.just(request1), AddPostRq.class)
            .exchange()
            .expectStatus().isOk()
            .returnResult(PostRs.class)
            .getResponseBody()
            .blockFirst();

        final AddCommentRq request2 = DataProvider.prepareAddCommentRq().build();
        webTestClient.post().uri("/api/v1/comments")
            .body(Mono.just(request2), AddCommentRq.class)
            .exchange()
            .expectStatus().isOk()
            .returnResult(CommentRs.class)
            .getResponseBody()
            .blockFirst();

        webTestClient.delete().uri("/api/v1/comments/{id}", 1L)
            .exchange()
            .expectStatus().isOk();

        assertThat(commentRepository.findById(1L)).isEmpty();
    }

    @Test
    void updateCommentShouldWork() {
        final AddPostRq request1 = DataProvider.prepareAddPostRq().build();
        webTestClient.post().uri("/api/v1/posts")
            .body(Mono.just(request1), AddPostRq.class)
            .exchange()
            .expectStatus().isOk()
            .returnResult(PostRs.class)
            .getResponseBody()
            .blockFirst();

        final AddCommentRq request2 = DataProvider.prepareAddCommentRq().build();
        webTestClient.post().uri("/api/v1/comments")
            .body(Mono.just(request2), AddCommentRq.class)
            .exchange()
            .expectStatus().isOk()
            .returnResult(CommentRs.class)
            .getResponseBody()
            .blockFirst();

        UpdateCommentRq updateCommentRq = UpdateCommentRq.builder()
            .comment("Hello2")
            .id(1L)
            .build();

        webTestClient.put().uri("/api/v1/comments")
            .body(Mono.just(updateCommentRq), UpdateCommentRq.class)
            .exchange()
            .expectStatus().isOk()
            .returnResult(CommentRs.class)
            .getResponseBody()
            .blockFirst();

        assertThat(commentRepository.findById(1L).get())
            .isEqualTo(Comment.builder()
                .id(1L)
                .value("Hello3")
                .build());


    }
}

