package test.controllers;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import test.initializer.Postgres;

@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(initializers = Postgres.Initializer.class)
@Transactional
public class IntegrationTestBase {

    @BeforeAll
    static void init() {
        Postgres.CONTAINER.start();
    }
}
