package tests.baseTests;

import config.SettingTest;
import org.junit.jupiter.api.BeforeEach;

public class BaseTestApi {
    @BeforeEach
    void beforeAll() {
        new SettingTest().configureApi();
    }
}
