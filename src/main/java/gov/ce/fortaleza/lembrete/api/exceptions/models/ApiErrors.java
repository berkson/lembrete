package gov.ce.fortaleza.lembrete.api.exceptions.models;

import lombok.*;

import java.util.List;

/**
 * Created by berkson
 * Date: 08/01/2022
 * Time: 12:20
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrors {
    @Singular
    private List<ApiError> errors;
}
