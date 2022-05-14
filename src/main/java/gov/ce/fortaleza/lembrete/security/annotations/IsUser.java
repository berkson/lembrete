package gov.ce.fortaleza.lembrete.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by berkson
 * Date: 17/01/2022
 * Time: 22:09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize(value = "hasAnyRole('PUBLICO_SIGECON', 'ADMIN', 'SUPORTE') || hasAnyAuthority('publico_sigecon')")
public @interface IsUser {
}
