package config.app;

import org.aeonbits.owner.ConfigFactory;

public class AppConfigProvider {
    public static AppConfig appConfig = ConfigFactory.create(AppConfig.class, System.getProperties());
}
