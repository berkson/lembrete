package gov.ce.fortaleza.lembrete.services.business;

/**
 * Created by berkson
 * Date: 06/01/2022
 * Time: 21:05
 */
public interface NotifyService {
    /**
     * Verifica e realiza o agendamento do(s) Trabalho(s).
     *
     * @param object objeto a ser verificado
     */
    void verifyAndSchedule(Object object);
}
