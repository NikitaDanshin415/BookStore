package config.user;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
    "system:env",
    "system:properties",
    "classpath:config/userConfig.properties",
})
public interface UserConfig extends Config {
    String login();

    String password();
}