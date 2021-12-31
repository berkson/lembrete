package gov.ce.fortaleza.lembrete.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Classe de configuração para envio de email
 * 
 * @author Berkson Ximenes
 * @since 31/12/2021
 */
@Configuration
@PropertySource("classpath:sendemail.properties")
public class EmailConfig {

    @Value("${email.host}")
    private String host;
    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String password;

    /**
     * Retorna um JavaMailSender com as propriedades necessárias para o envio de
     * email através do servidor de email.
     *
     * @return JavaMailSender
     */
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(587);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }

}
