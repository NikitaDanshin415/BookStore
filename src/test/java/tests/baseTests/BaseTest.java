package tests.baseTests;

import api.helpers.Attacher;
import config.SettingTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public abstract class BaseTest {
    @BeforeEach
    void beforeAll() {
        new SettingTest().configure();
        open("");
    }

    @AfterEach
    void addAttachments() {
        Attacher.screenshotAs("Last screenshot");
        Attacher.pageSource();
        Attacher.browserConsoleLogs();
        Attacher.addVideo();

        closeWebDriver();
    }

    /**
     * Добавить куки в браузер селенида.
     */
    protected void setCookieToWebDriver(Cookie cookie){
        getWebDriver().manage().addCookie(cookie);
    }
}
