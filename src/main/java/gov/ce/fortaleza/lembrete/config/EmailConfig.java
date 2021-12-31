package gov.ce.fortaleza.lembrete.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Classe de configuração para envio de email
 * 
 * @author berkson
 * @since 13/07/2020
 */
@Configuration
public class EmailConfig {
    
    private final Environment env;
    
    @Autowired
    public EmailConfig(Environment env) {
        this.env = env;
    }

    /**
     * Retorna um JavaMailSender com as propriedades necessárias para o envio de
     * email através do servidor de email.
     *
     * @return JavaMailSender
     */
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(env.getProperty("email.host"));
        mailSender.setPort(587);

        mailSender.setUsername(env.getProperty("email.username"));
        mailSender.setPassword(env.getProperty("email.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }

}
