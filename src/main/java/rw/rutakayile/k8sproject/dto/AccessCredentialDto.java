package rw.rutakayile.k8sproject.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AccessCredentialDto {
    @NotNull(message = "Client ID must be provided")
    private Long clientId;

    @NotNull(message = "Monthly limit must be provided")
    @Min(value = 1, message = "Limit can not be less than 1 request per month")
    @Max(value = 10, message = "Limit can not be greater than 10 request per month")
    private Integer monthlyLimit;
}
