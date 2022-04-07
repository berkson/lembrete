package gov.ce.fortaleza.lembrete.exceptions;

import gov.ce.fortaleza.lembrete.utils.MessagesUtil;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author Berkson Ximenes
 * @since 05/10/2020
 */
public class CustomDataIntegrityException extends Exception {

    private static final long serialVersionUID = -7027336526102265522L;
    private final String messageKey;
    private final Locale locale;
    private final Object[] args;

    public CustomDataIntegrityException(String messageKey) {
        this(messageKey, null, LocaleContextHolder.getLocale());
    }

    public CustomDataIntegrityException(String messageKey, Object[] args) {
        this(messageKey, args, LocaleContextHolder.getLocale());
    }

    public CustomDataIntegrityException(String messageKey, Object[] args, Locale locale) {
        this.messageKey = messageKey;
        this.locale = locale;
        this.args = args;
    }

    public String getLocalizedMessage() {
        return MessagesUtil.getMessageForLocale(messageKey, args, locale);
    }

}
