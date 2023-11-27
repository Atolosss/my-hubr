package com.example.controllers;

import com.example.exceptions.ServiceException;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.PostRs;
import com.example.model.entity.Post;
import com.example.support.DataProvider;
import com.example.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@ActiveProfiles("test")
class PostControllerTest extends IntegrationTestBase {

    private static final String COMMENTS = "comments";
    private static final String AUDIT = "audit";
    private static final String API = "api";
    private static final String V_1 = "v1";
    private static final String POSTS = "posts";

    @Test
    void addPostShouldWork() {
        final AddPostRq request = DataProvider.prepareAddPostRq()
            .name("test")
            .description("test")
            .build();

        assertThat(postPost(request, 200))
            .isEqualTo(PostRs.builder()
                .id(1L)
                .name(request.getName())
                .description(request.getDescription())
                .comments(List.of())
                .build());

        executeInTransaction(() -> assertThat(postRepository.findAll())
            .first()
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Post.builder()
                .id(1L)
                .name(request.getName())
                .description(request.getDescription())
                .build()));
    }

    private PostRs postPost(final AddPostRq request, final int status) {
        return webTestClient.post()
            .uri(uriBuilder -> uriBuilder
                .pathSegment(API, V_1, POSTS)
                .build())
            .bodyValue(request)
            .exchange()
            .expectStatus().isEqualTo(status)
            .expectBody(PostRs.class)
            .returnResult()
            .getResponseBody();
    }

    private PostRs getPost(final Long id, final int status) {
        return webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .pathSegment(API, V_1, POSTS, String.valueOf(id))
                .build())
            .exchange()
            .expectStatus().isEqualTo(status)
            .expectBody(PostRs.class)
            .returnResult()
            .getResponseBody();
    }

    @Test
    void getPostsBetweenDatesShouldReturnFilteredPosts() {
        final Post post = DataProvider.preparePost(List.of()).build();
        createPost(post);

        final LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        final LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);

        final List<PostRs> posts = webTestClient.get().uri(uriBuilder -> uriBuilder
                .pathSegment(API, V_1, POSTS)
                .queryParam("startDate", startDate.format(DateTimeFormatter.ISO_DATE_TIME))
                .queryParam("endDate", endDate.format(DateTimeFormatter.ISO_DATE_TIME))
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(PostRs.class)
            .returnResult()
            .getResponseBody();
        assertThat(posts).isNotNull();
        assertThat(posts).isEqualTo(postService.getSortedPosts(null));
    }

    @Test
    void getPostShouldWork() {
        final Post post = DataProvider.preparePost(List.of()).build();
        createPost(post);

        assertThat(getPost(post.getId(), 200))
            .usingRecursiveComparison()
            .ignoringFields(COMMENTS, AUDIT)
            .isEqualTo(Post.builder()
                .id(post.getId())
                .name(post.getName())
                .description(post.getDescription())
                .build());
    }

    @Test
    void getPostsShouldWork() {
        final Post post = DataProvider.preparePost(List.of()).build();
        createPost(post);

        assertThat(getPosts(200))
            .usingRecursiveComparison()
            .ignoringFields(COMMENTS)
            .isEqualTo(List.of(PostRs.builder()
                .id(post.getId())
                .name(post.getName())
                .description(post.getDescription())));
    }

    private List<PostRs> getPosts(final int status) {
        return webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .pathSegment(API, V_1, POSTS)
                .build())
            .exchange()
            .expectStatus().isEqualTo(status)
            .expectBody(new ParameterizedTypeReference<List<PostRs>>() {
            }).returnResult()
            .getResponseBody();
    }

    @Test
    void deletePostShouldWork() {
        final Post post = DataProvider.preparePost(List.of()).build();
        createPost(post);
        deletePost(post.getId());

        assertThat(postRepository.findById(post.getId()))
            .isEmpty();
    }

    private void deletePost(final Long id) {
        webTestClient.delete()
            .uri(uriBuilder -> uriBuilder
                .pathSegment(API, V_1, POSTS, String.valueOf(id))
                .build())
            .exchange()
            .expectStatus().isEqualTo(200);
    }

    @Test
    void exceptionTest() {
        final ServiceException exception = assertThrows(ServiceException.class, () -> {
            postService.getPost(3L, false);
        });
        assertThat(exception.getMessage()).isEqualTo("Post with id " + 3 + " not found");
    }
}
