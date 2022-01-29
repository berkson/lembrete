package gov.ce.fortaleza.lembrete.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Berkson Ximenes
 * @since 28/01/2022
 */
public final class CodeGenerator {

    private CodeGenerator() {
        throw new IllegalStateException("Code Generator Class");
    }

    /**
     * Retorna um código que pode ser usado de várias maneiras. No caso é usado
     * para recuperação de senha.
     *
     * @return string com código de 10 caracteres
     */
    public static String generate() {
        final String MIN = "a b c d e f g h i j k l m n o p q r s t u v w x y z";
        final String SIGNALS_AND_NUMBERS = " 0 1 2 3 4 5 6 7 8 9 ! @ # $ % & * + - _ ";
        final String ALL_CHARS = MIN + SIGNALS_AND_NUMBERS + MIN.toUpperCase();
        String[] letters;
        List<String> font = new ArrayList<>();
        StringBuilder code = new StringBuilder();

        letters = ALL_CHARS.split(" ");
        font.addAll(Arrays.asList(letters));
        Collections.shuffle(font);

        font.parallelStream().skip(62).forEach(code::append);

        return code.toString();
    }

}
