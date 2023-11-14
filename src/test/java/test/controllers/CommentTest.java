package test.controllers;

import com.example.contoller.CommentController;
import com.example.model.dto.AddCommentRq;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;
import test.initializer.Postgres;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {Postgres.Initializer.class})
@Testcontainers
public class CommentTest {

    @Autowired(required = false)
    private CommentController commentController;

    @BeforeAll
    static void init() {
        Postgres.CONTAINER.start();
    }

    @Test
    public void newCommentShouldSaveInBd() {
        AddCommentRq addCommentRq = AddCommentRq.builder()
            .comment("rere")
            .postId(1L)
            .build();

        commentController.add(addCommentRq);

        assertThat(addCommentRq.getComment()).matches(commentController.get(1L).getValue());

    }
}
