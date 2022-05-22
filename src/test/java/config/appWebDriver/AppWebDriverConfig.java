package config.appWebDriver;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
    "system:properties",
    "system:env",
    "classpath:config/userConfig.properties"
})
public interface AppWebDriverConfig extends Config {
    @Key("browser")
    @DefaultValue("chrome")
    String getBrowser();

    @Key("isRemote")
    @DefaultValue("false")
    Boolean isRemote();

    @Key("remoteLogin")
    String getRemoteLogin();

    @Key("remotePassword")
    String getRemotePassword();

    @Key("remoteDriverUrl")
    String getRemoteDriverUrl();
}
