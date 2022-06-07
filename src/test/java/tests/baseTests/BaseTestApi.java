package tests.baseTests;

import config.Configure;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTestApi {
    @BeforeAll
    static void beforeAll() {
        new Configure().configureApi();
    }
}