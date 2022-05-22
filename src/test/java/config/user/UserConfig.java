package config.user;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
    "classpath:config/userConfig.properties",
    "system:properties",
    "system:env",
})
public interface UserConfig extends Config {
    @Config.Key("login")
    String getLogin();

    @Config.Key("password")
    String getPassword();
}
