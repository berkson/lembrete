package gov.ce.fortaleza.lembrete.exceptions;

/**
 *
 * @author Berkson Ximenes
 * @since 05/10/2020
 */
public class SendMailException extends Exception {

    private static final long serialVersionUID = -7027336526102265522L;

    public SendMailException(String msg) {
        super(msg);
    }

    public SendMailException(Throwable err) {
        super(err);
    }

    public SendMailException(String msg, Throwable err) {
        super(msg, err);
    }

}
