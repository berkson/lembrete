package gov.ce.fortaleza.lembrete.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by berkson
 * Date: 10/01/2022
 * Time: 19:13
 */
@Configuration
public class GeneralBeansConfig {

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

}
