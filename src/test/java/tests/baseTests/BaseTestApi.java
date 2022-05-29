package tests.baseTests;

import config.SettingTest;
import org.junit.jupiter.api.BeforeAll;
public abstract class BaseTestApi {
    @BeforeAll
    static void beforeAll() {
        new SettingTest().configureApi();
    }
}
