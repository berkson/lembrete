package gov.ce.fortaleza.lembrete.services.errors;

import gov.ce.fortaleza.lembrete.exceptions.CustomDataIntegrityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by berkson
 * Date: 02/04/2022
 * Time: 10:15
 */
@Service
@Slf4j
public class HandleContractErrorsImpl implements HandleErrorsService {

    private static final String CONTRACT_NUMBER = "contract_number";


    @Override
    public void handleDataIntegrityErros(@NotNull DataIntegrityViolationException e) throws
            CustomDataIntegrityException {
        String[] breakString;

        breakString = Optional.of(Objects
                        .requireNonNull(e.getRootCause()).getMessage()
                        .split(":"))
                .orElse(new String[1]);
        if (breakString[breakString.length - 1].toLowerCase().contains(CONTRACT_NUMBER)) {
            String s = breakString[breakString.length - 1].toLowerCase();
            Pattern pattern = Pattern.compile("([\\d])+/([2][\\d]{3})");
            Matcher matcher = pattern.matcher(s);
            boolean oi = matcher.find();
            if (oi) {
                System.out.println("Contrato: " + matcher.group());
                throw new CustomDataIntegrityException("invalid.contractNumber",
                        new Object[]{matcher.group()}, LocaleContextHolder.getLocale());
            }
        }
        throw new CustomDataIntegrityException("error.dataIntegrity");
    }
}
