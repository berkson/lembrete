package gov.ce.fortaleza.lembrete.exceptions;

import gov.ce.fortaleza.lembrete.utils.MessagesUtil;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author Berkson Ximenes
 * @since 05/10/2020
 */
public class InvalidRecoveryCodeException extends Exception {

    private static final long serialVersionUID = -7027336526102265522L;
    private final String messageKey;
    private final Locale locale;

    public InvalidRecoveryCodeException(String messageKey) {
        this(messageKey, LocaleContextHolder.getLocale());
    }

    public InvalidRecoveryCodeException(String messageKey, Locale locale) {
        this.messageKey = messageKey;
        this.locale = locale;
    }

    public String getLocalizedMessage() {
        return MessagesUtil.getMessageForLocale(messageKey, locale);
    }

}
