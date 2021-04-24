package rw.rutakayile.k8sproject.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ClientDto {
    @NotNull(message = "Client must have a name")
    @NotBlank(message = "Client name must not be empty")
    private String names;
}
