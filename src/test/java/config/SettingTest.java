package config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.app.AppConfigProvider;
import config.appWebDriver.AppWebDriverConfigProvider;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SettingTest {
    public void configure(){
        Configuration.browser = AppWebDriverConfigProvider.AppConfigWebDriver.getBrowser();
        Configuration.baseUrl = AppConfigProvider.appConfig.getUrl();
        RestAssured.baseURI = AppConfigProvider.appConfig.getApiUrl();

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (AppWebDriverConfigProvider.AppConfigWebDriver.isRemote()) {
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
            Configuration.remote = "https://"
                + AppWebDriverConfigProvider.AppConfigWebDriver.getRemoteLogin()
                + ":" + AppWebDriverConfigProvider.AppConfigWebDriver.getRemotePassword()
                + "@"
                + AppWebDriverConfigProvider.AppConfigWebDriver.getRemoteDriverUrl()
                + "/wd/hub";
        }

        Configuration.browserCapabilities = capabilities;
    }

    public void configureApi(){
        RestAssured.baseURI = AppConfigProvider.appConfig.getApiUrl();
    }
}
