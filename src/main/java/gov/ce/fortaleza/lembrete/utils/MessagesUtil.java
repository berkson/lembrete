package gov.ce.fortaleza.lembrete.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by berkson
 * Date: 28/01/2022
 * Time: 22:08
 */
public class MessagesUtil {
    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle("br.gov.messages", locale)
                .getString(messageKey);
    }
}
