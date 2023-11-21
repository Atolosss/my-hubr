package com.example.controllers;

import com.example.model.dto.AddCommentRq;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.CommentRs;
import com.example.model.dto.PostRs;
import com.example.model.dto.UpdateCommentRq;
import com.example.model.entity.Comment;
import com.example.support.DataProvider;
import com.example.support.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class CommentControllerTest extends IntegrationTestBase {

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
    void addCommentShouldWork() {
        final AddPostRq request1 = DataProvider.prepareAddPostRq().build();
        final ResponseEntity<PostRs> entity1 = testRestTemplate.postForEntity("/api/v1/posts",
            request1, PostRs.class);
        final AddCommentRq request2 = DataProvider.prepareAddCommentRq().build();
        final ResponseEntity<CommentRs> entity2 = testRestTemplate.postForEntity("/api/v1/comments",
            request2, CommentRs.class);

        assertThat(entity2)
            .extracting(HttpEntity::getBody)
            .isEqualTo(CommentRs.builder()
                .id(1L)
                .value("Hello")
                .build());

        assertThat(commentRepository.findById(1L).get())
            .isEqualTo(Comment.builder()
                .id(1L)
                .value("Hello")
                .post(postRepository.findById(entity1.getBody().getId()).get())
                .build());
    }

    @Test
    void getCommentShouldWork() {
        final AddPostRq request1 = DataProvider.prepareAddPostRq().build();
        testRestTemplate.postForEntity("/api/v1/posts",
            request1, PostRs.class);
        final AddCommentRq request2 = DataProvider.prepareAddCommentRq().build();
        testRestTemplate.postForEntity("/api/v1/comments",
            request2, CommentRs.class);

        final ResponseEntity<CommentRs> entity2 = testRestTemplate.getForEntity("/api/v1/comments/{id}",
            CommentRs.class, 1L);

        assertThat(entity2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity2.getBody())
            .isEqualTo(CommentRs.builder()
                .id(1L)
                .value("Hello")
                .build());
    }

    @Test
    void getCommentsShouldWork() {
        final AddPostRq request1 = DataProvider.prepareAddPostRq().build();
        testRestTemplate.postForEntity("/api/v1/posts",
            request1, PostRs.class);
        final AddCommentRq request2 = DataProvider.prepareAddCommentRq().build();
        testRestTemplate.postForEntity("/api/v1/comments",
            request2, CommentRs.class);

        ResponseEntity<List<CommentRs>> entity1 = testRestTemplate.exchange(
            "/api/v1/comments",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<CommentRs>>() {
            });
        List<CommentRs> comments = new ArrayList<>();
        comments.add(CommentRs.builder()
            .id(1L)
            .value("Hello")
            .build());

        assertThat(entity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity1.getBody())
            .isEqualTo(comments);
    }

    @Test
    void deleteCommentShouldWork() {
        final AddPostRq request1 = DataProvider.prepareAddPostRq().build();
        testRestTemplate.postForEntity("/api/v1/posts",
            request1, PostRs.class);
        final AddCommentRq request2 = DataProvider.prepareAddCommentRq().build();
        testRestTemplate.postForEntity("/api/v1/comments",
            request2, CommentRs.class);
        testRestTemplate.delete("/api/v1/comments/{id}", 1L);

        assertThat(commentRepository.findById(1L)).isEmpty();
    }

    @Test
    void updateCommentShouldWork() {
        final AddPostRq request1 = DataProvider.prepareAddPostRq().build();
        testRestTemplate.postForEntity("/api/v1/posts",
            request1, PostRs.class);
        final AddCommentRq request2 = DataProvider.prepareAddCommentRq().build();
        testRestTemplate.postForEntity("/api/v1/comments",
            request2, CommentRs.class);
        UpdateCommentRq updateCommentRq = UpdateCommentRq.builder()
            .id(1L)
            .comment("Hello1")
            .build();

        ResponseEntity<CommentRs> entity = testRestTemplate.exchange(
            "/api/v1/comments",
            HttpMethod.PUT,
            new HttpEntity<>(updateCommentRq),
            CommentRs.class
        );

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(commentRepository.findById(1L).get())
            .isEqualTo(Comment.builder()
                .id(1L)
                .value("Hello1")
                .build());
    }
}
