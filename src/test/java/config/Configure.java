package config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.app.AppConfigProvider;
import config.appWebDriver.AppWebDriverConfigProvider;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.openqa.selenium.remote.DesiredCapabilities;

import static java.lang.String.format;

public class Configure {
    public void configure() {
        Configuration.browser = AppWebDriverConfigProvider.AppConfigWebDriver.browser();
        Configuration.baseUrl = AppConfigProvider.appConfig.url();
        RestAssured.baseURI = AppConfigProvider.appConfig.apiUrl();

        if (AppWebDriverConfigProvider.AppConfigWebDriver.isRemote()) {
            configureSelenoid();
        }

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    public void configureApi() {
        RestAssured.baseURI = AppConfigProvider.appConfig.apiUrl();
    }

    private void configureSelenoid() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

        Configuration.remote = format("https://%s:%s@%s/wd/hub",
            AppWebDriverConfigProvider.AppConfigWebDriver.remoteLogin(),
            AppWebDriverConfigProvider.AppConfigWebDriver.remotePassword(),
            AppWebDriverConfigProvider.AppConfigWebDriver.remoteDriverUrl()
        );
    }
}