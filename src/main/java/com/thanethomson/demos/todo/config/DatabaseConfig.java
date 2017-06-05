package com.thanethomson.demos.todo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.VaultResponse;

import javax.sql.DataSource;
import java.util.Map;

/**
 * If Vault support is enabled, this will attempt to obtain credentials from Vault. Otherwise
 * it will attempt to grab credentials from the Spring environment configuration.
 */
@Configuration
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${vault.enabled}")
    Boolean vaultEnabled;

    @Bean(name = "hikariDataSource", destroyMethod = "close")
    public DataSource dataSource(Environment env, VaultOperations vault) {
        String username, password;

        if (vaultEnabled) {
            VaultResponse vaultResponse = vault.read("database/creds/todo");
            Map<String, Object> creds = vaultResponse.getData();
            username = (String)creds.get("username");
            password = (String)creds.get("password");
            logger.info("Obtained database credentials from Vault");
        } else {
            username = env.getProperty("spring.datasource.username");
            password = env.getProperty("spring.datasource.password");
            logger.info("Obtained database credentials from Spring configuration");
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getProperty("spring.datasource.url"));
        config.setUsername(username);
        config.setPassword(password);

        return new HikariDataSource(config);
    }

}
