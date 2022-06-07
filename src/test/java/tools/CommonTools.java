package tools;

import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CommonTools {
    /**
     * Добавить куки в браузер селенида.
     */
    public void setCookieToWebDriver(String name, String value, String domain, String path) {
        Cookie cookie = new Cookie(name,
            value,
            domain,
            path,
            null);

        getWebDriver().manage().addCookie(cookie);
    }
}