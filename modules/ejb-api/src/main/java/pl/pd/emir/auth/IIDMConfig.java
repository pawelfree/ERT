package pl.pd.emir.auth;

import pl.pd.emir.auth.config.UserServiceConfiguration;

public interface IIDMConfig {

    UserServiceConfiguration getConfig();

    UserServiceConfiguration getUserConfig(String configFile);

    void setConfigurationFilePath(String configPath);
}
