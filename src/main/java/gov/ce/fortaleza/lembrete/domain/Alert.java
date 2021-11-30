package gov.ce.fortaleza.lembrete.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by berkson
 * Date: 29/11/2021
 * Time: 23:29
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    private String code;
    private Integer time;
}
