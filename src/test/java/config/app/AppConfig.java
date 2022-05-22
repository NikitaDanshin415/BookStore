package config.app;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
    "system:properties",
    "system:env",
    "classpath:config/appConfig.properties"
})
public interface AppConfig extends Config{
    @Config.Key("url")
    String getUrl();

    @Config.Key("apiUrl")
    String getApiUrl();
}
