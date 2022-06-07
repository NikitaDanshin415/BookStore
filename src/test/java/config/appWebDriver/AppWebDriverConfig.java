package config.appWebDriver;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
    "system:properties",
    "system:env",
    "classpath:config/userConfig.properties"
})
public interface AppWebDriverConfig extends Config {
    @DefaultValue("chrome")
    String browser();

    @DefaultValue("false")
    Boolean isRemote();

    String remoteLogin();

    String remotePassword();

    String remoteDriverUrl();
}