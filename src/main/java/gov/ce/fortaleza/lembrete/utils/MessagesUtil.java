package gov.ce.fortaleza.lembrete.utils;

import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by berkson
 * Date: 28/01/2022
 * Time: 22:08
 */
public class MessagesUtil {

    private static MessageSource messageSource;

    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle("br.gov.messages", locale)
                .getString(messageKey);
    }

    public static String getMessageForLocale(String messageKey, Object[] args, Locale locale) {
        return messageSource.getMessage(messageKey, args, locale);
    }

    public static void setMessageSource(MessageSource messageSource) {
        MessagesUtil.messageSource = messageSource;
    }
}
