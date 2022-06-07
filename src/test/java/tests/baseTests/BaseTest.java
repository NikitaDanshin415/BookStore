package tests.baseTests;

import tools.AllureAttach;
import config.Configure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTest {
    @BeforeEach
    void beforeEach() {
        open("");
    }

    @BeforeAll
    static void beforeAll() {
        new Configure().configure();
    }

    @AfterEach
    void addAttachments() {
        AllureAttach.screenshotAs("Last screenshot");
        AllureAttach.pageSource();
        AllureAttach.browserConsoleLogs();
        AllureAttach.addVideo();

        closeWebDriver();
    }
}