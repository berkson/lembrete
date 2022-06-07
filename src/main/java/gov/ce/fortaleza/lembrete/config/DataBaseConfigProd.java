package gov.ce.fortaleza.lembrete.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

/**
 * Created by berkson
 * Date: 29/12/2021
 * Time: 14:16
 */
@Configuration
@Profile({"prod"})
@PropertySource("classpath:dbsigecon-prod.properties")
@EnableAutoConfiguration
public class DataBaseConfigProd {

    @Value("${db.user}")
    private String user;
    @Value("${db.pass}")
    private String pass;
    @Value("${db.url}")
    private String url;
    @Value("${db.driver}")
    private String driver;

    @Primary
    @Bean(name = "dbsigecon")
    @QuartzDataSource
    public DataSource postgresDataSource() {

        return DataSourceBuilder
                .create()
                .username(this.user).password(this.pass)
                .url(this.url).driverClassName(this.driver).build();
    }
}
