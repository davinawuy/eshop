package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
        // This test method is intentionally left empty.
        // Its sole purpose is to verify that the Spring application context loads without issues.
    }

    @Test
    void mainMethodRuns() {
        assertDoesNotThrow(() -> EshopApplication.main(new String[] {}));
    }
}
