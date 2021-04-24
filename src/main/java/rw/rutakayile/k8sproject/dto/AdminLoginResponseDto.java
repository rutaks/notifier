package rw.rutakayile.k8sproject.dto;

import lombok.Data;
import rw.rutakayile.k8sproject.model.Admin;

@Data
public class AdminLoginResponseDto {
    private Admin admin;
    private String token;
}
