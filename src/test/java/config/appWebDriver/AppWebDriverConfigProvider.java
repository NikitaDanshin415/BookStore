package config.appWebDriver;

import org.aeonbits.owner.ConfigFactory;

public class AppWebDriverConfigProvider {
    public static AppWebDriverConfig AppConfigWebDriver = ConfigFactory.create(AppWebDriverConfig.class, System.getProperties());
}