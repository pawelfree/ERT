package pl.pd.emir.auth;

import java.io.*;
import java.text.MessageFormat;
import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.LoggerFactory;
import pl.pd.emir.admin.ParameterManager;
import pl.pd.emir.auth.config.LdapConfig;
import pl.pd.emir.auth.config.LdapPassword;
import pl.pd.emir.auth.config.UserServiceConfiguration;
import pl.pd.emir.commons.commonutils.PasswordUtils;
import pl.pd.emir.enums.ParameterKey;

@Stateless
@Local(IIDMConfig.class)
public class IDMConfig implements IIDMConfig {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IDMConfig.class);
    @EJB
    private ParameterManager parameterManager;
    private String configPath;
    private UserServiceConfiguration config;

    public IDMConfig() {
    }

    @Override
    public void setConfigurationFilePath(String configPath) {
        this.configPath = configPath;
    }

    @PostConstruct
    private void init() {
        LOGGER.info("Inicjalizacja konfiguracji modulu IDM");
        configPath = parameterManager.getValue(ParameterKey.AUTH_CONFIG_PATH);
        config = getUserConfig(configPath);
        if (config != null) {
            encryptPassword();
        } else {
            LOGGER.error("Niepowodzenie podczas wczytywania konfiguracji z pliku " + configPath);
        }
    }

    @Override
    public UserServiceConfiguration getConfig() {
        return config;
    }

    @Override
    public UserServiceConfiguration getUserConfig(String configFile) {
        InputStream is = null;
        JAXBContext context;
        UserServiceConfiguration conf = null;
        try {
            if (configFile != null && new File(configFile).exists()) {
                is = new FileInputStream(configFile);
            } else {
                LOGGER.info(MessageFormat.format("Brak konfiguracji zewnetrznej w katalogu {0}, laduje konfiguracj\u0119 domyslna", configFile));
                is = this.getClass().getClassLoader().getResourceAsStream("META-INF/authConfig.xml");
            }
            context = JAXBContext.newInstance(UserServiceConfiguration.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            conf = (UserServiceConfiguration) unmarshaller.unmarshal(is);
        } catch (FileNotFoundException ex) {
            LOGGER.error("Brak pliku z konfiguracja ", ex);
        } catch (JAXBException ex) {
            LOGGER.error("Blad pobierania konfiguracji ", ex);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                LOGGER.error("Blad zamykania strumienia z plikiem konfiguracji ");
            }
        }
        return conf;
    }

    private void encryptPassword() {
        LdapConfig ldapConfig = config.getLdapConfig();
        if (ldapConfig != null && ldapConfig.getLdapUserPassword() != null) {
            if (LdapPassword.Type.PLAIN.name().equals(ldapConfig.getLdapUserPassword().getType())) {
                try {
                    String encPassword = PasswordUtils.encrypt(ldapConfig.getLdapUserPassword().getPassword());
                    config.getLdapConfig().getLdapUserPassword().setPassword(encPassword);
                    config.getLdapConfig().getLdapUserPassword().setType(LdapPassword.Type.ENCRYPTED.name());
                    updateConfigFile();
                } catch (Exception ex) {
                    LOGGER.error("Blad szyfrowania hasla", ex);
                }
            }
        }
    }

    private void updateConfigFile() {
        OutputStream out = null;
        JAXBContext context;
        try {
            out = new FileOutputStream(new File(configPath));
            context = JAXBContext.newInstance(UserServiceConfiguration.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(config, out);
        } catch (FileNotFoundException | JAXBException ex) {
            LOGGER.error("Blad zapisu konfiguracji ", ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                LOGGER.error("Blad zamykania strumienia z plikiem konfiguracji ", ex);
            }
        }
    }
}
