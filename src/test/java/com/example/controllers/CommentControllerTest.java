package com.example.controllers;

import com.example.exceptions.ServiceException;
import com.example.model.dto.AddCommentRq;
import com.example.model.dto.CommentRs;
import com.example.model.dto.UpdateCommentRq;
import com.example.model.entity.Comment;
import com.example.model.entity.Post;
import com.example.support.DataProvider;
import com.example.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class CommentControllerTest extends IntegrationTestBase {

    private static final String API = "api";
    private static final String V_1 = "v1";
    private static final String COMMENTS = "comments";

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
                .pathSegment(API, V_1, COMMENTS)
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
                .pathSegment(API, V_1, COMMENTS, String.valueOf(id))
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
                .pathSegment(API, V_1, COMMENTS)
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
                .pathSegment(API, V_1, COMMENTS, String.valueOf(id))
                .build())
            .exchange()
            .expectStatus().isEqualTo(200);
    }

    private CommentRs putComment(final UpdateCommentRq updateCommentRq) {
        return webTestClient.put()
            .uri(uriBuilder -> uriBuilder
                .pathSegment(API, V_1, COMMENTS)
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

    @Test
    void exceptionCommentTest() {
        final ServiceException exception = assertThrows(ServiceException.class, () -> {
            commentService.findById(3L);
        });
        assertThat(exception.getMessage()).isEqualTo("Comments with id " + 3 + " not found");
    }
    }

