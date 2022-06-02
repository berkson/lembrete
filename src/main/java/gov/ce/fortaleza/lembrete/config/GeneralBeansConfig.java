package gov.ce.fortaleza.lembrete.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.web.header.writers.ContentSecurityPolicyHeaderWriter;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by berkson
 * Date: 10/01/2022
 * Time: 19:13
 */
@Slf4j
@Configuration
public class GeneralBeansConfig {

    private static final String DEFAULT_SRC_SELF_POLICY = "default-src 'self'; frame-ancestors 'self'; " +
            "form-action 'self'";

    /*
     * Permite que a fábrica de validadores do hibernate suporte injeção de
     * dependência do spring
     */
    @Bean
    public Validator validator(final AutowireCapableBeanFactory acbf) {
        ValidatorFactory validatorFactory = Validation
                .byProvider(HibernateValidator.class).configure()
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(acbf))
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource
                .setBasenames("classpath:br/gov/messages",
                        "classpath:org/springframework/security/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public ContentSecurityPolicyHeaderWriter writer(){
        return new ContentSecurityPolicyHeaderWriter(DEFAULT_SRC_SELF_POLICY);
    }

}
