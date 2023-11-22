package com.example.controllers;

import com.example.model.dto.AddCommentRq;
import com.example.model.dto.CommentRs;
import com.example.model.dto.UpdateCommentRq;
import com.example.model.entity.Comment;
import com.example.model.entity.Post;
import com.example.support.DataProvider;
import com.example.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CommentControllerTest extends IntegrationTestBase {

    @Test
    void postCommentShouldWork() {
        final Post post = DataProvider.preparePost(List.of()).build();
        createPost(post);

        final AddCommentRq request = DataProvider.prepareAddCommentRq()
            .postId(post.getId())
            .build();

        assertThat(postComment(request, 200))
            .isEqualTo(CommentRs.builder()
                .id(1L)
                .value("Hello")
                .build());

        executeInTransaction(() -> assertThat(commentRepository.findAll())
            .first()
            .usingRecursiveComparison()
            .ignoringFields("post", "audit")
            .isEqualTo(Comment.builder()
                .id(1L)
                .value("Hello")
                .build()));

    }

    @Test
    void getCommentShouldWork() {
        final Comment comment = createPostAndRetrieveSecondComment();
        assertThat(getComment(comment.getId(), 200))
            .usingRecursiveComparison()
            .isEqualTo(CommentRs.builder()
                .id(comment.getId())
                .value(comment.getValue())
                .build());
    }

    @Test
    void getCommentsShouldWork() {
        final Comment comment1 = Comment.builder()
            .value("comment first")
            .build();
        final Comment comment2 = Comment.builder()
            .value("comment second")
            .build();
        final Post post = DataProvider.preparePost(List.of(comment1, comment2))
            .build();

        createPost(post);

        assertThat(getComments(200))
            .usingRecursiveComparison()
            .isEqualTo(List.of(
                CommentRs.builder()
                    .id(comment1.getId())
                    .value(comment1.getValue())
                    .build(),
                CommentRs.builder()
                    .id(comment2.getId())
                    .value(comment2.getValue())
                    .build()
            ));
    }

    @Test
    void deleteCommentShouldWork() {
        final Comment comment = createPostAndRetrieveSecondComment();
        deleteComment(comment.getId());

        assertThat(commentRepository.findById(comment.getId()))
            .isEmpty();
    }

    @Test
    void updateCommentShouldWork() {
        final Comment comment = createPostAndRetrieveSecondComment();

        final UpdateCommentRq updateCommentRq = DataProvider.prepareUpdateCommentRq()
            .id(comment.getId())
            .build();
        assertThat(putComment(updateCommentRq))
            .isNotNull();
        assertThat(commentRepository.findById(comment.getId()))
            .isNotEmpty()
            .get()
            .usingRecursiveComparison()
            .ignoringFields("post", "audit")
            .isEqualTo(Comment.builder()
                .id(comment.getId())
                .value("Hello1")
                .build());
    }

    private CommentRs postComment(final AddCommentRq request, final int status) {
        return webTestClient.post()
            .uri(uriBuilder -> uriBuilder
                .pathSegment("api", "v1", "comments")
                .build())
            .bodyValue(request)
            .exchange()
            .expectStatus().isEqualTo(status)
            .expectBody(CommentRs.class)
            .returnResult()
            .getResponseBody();
    }

    private CommentRs getComment(final Long id, final int status) {
        return webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .pathSegment("api", "v1", "comments", String.valueOf(id))
                .build())
            .exchange()
            .expectStatus().isEqualTo(status)
            .expectBody(CommentRs.class)
            .returnResult()
            .getResponseBody();
    }

    private List<CommentRs> getComments(final int status) {
        return webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .pathSegment("api", "v1", "comments")
                .build())
            .exchange()
            .expectStatus().isEqualTo(status)
            .expectBody(new ParameterizedTypeReference<List<CommentRs>>() {
            })
            .returnResult()
            .getResponseBody();
    }

    private void deleteComment(final Long id) {
        webTestClient.delete()
            .uri(uriBuilder -> uriBuilder
                .pathSegment("api", "v1", "comments", String.valueOf(id))
                .build())
            .exchange()
            .expectStatus().isEqualTo(200);
    }

    private CommentRs putComment(final UpdateCommentRq updateCommentRq) {
        return webTestClient.put()
            .uri(uriBuilder -> uriBuilder
                .pathSegment("api", "v1", "comments")
                .build())
            .bodyValue(updateCommentRq)
            .exchange()
            .expectStatus().isEqualTo(200)
            .expectBody(CommentRs.class)
            .returnResult()
            .getResponseBody();
    }

    private Comment createPostAndRetrieveSecondComment() {
        final Comment comment1 = Comment.builder()
            .value("comment first")
            .build();
        final Comment comment2 = Comment.builder()
            .value("comment second")
            .build();
        final Post post = DataProvider.preparePost(List.of(comment1, comment2))
            .build();

        createPost(post);
        return comment2;
    }
}
