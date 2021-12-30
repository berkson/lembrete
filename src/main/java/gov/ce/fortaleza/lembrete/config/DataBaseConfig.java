package gov.ce.fortaleza.lembrete.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

/**
 * Created by berkson
 * Date: 29/12/2021
 * Time: 14:16
 */
@Configuration
@Profile({"default", "dev"})
@PropertySource("classpath:lembretedb-dev.properties")
public class DataBaseConfig {

    @Value("${db.user}")
    private String user;
    @Value("${db.pass}")
    private String pass;
    @Value("${db.url}")
    private String url;
    @Value("${db.driver}")
    private String driver;

    @Primary
    @Bean(name = "lembretedb")
    public DataSource postgresDataSource() {

        return DataSourceBuilder
                .create()
                .username(this.user).password(this.pass)
                .url(this.url).driverClassName(this.driver).build();
    }
}
