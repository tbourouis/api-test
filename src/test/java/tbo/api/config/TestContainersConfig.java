package tbo.api.config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;

public class TestContainersConfig {

    // ✅ Singleton container: Start once, reuse for all tests
    @ServiceConnection
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>("postgres:15-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    static {
        POSTGRES_CONTAINER.start();  // Start container only once
    }

    public static PostgreSQLContainer<?> getInstance() {
        return POSTGRES_CONTAINER;
    }
}
