package rw.rutakayile.k8sproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.rutakayile.k8sproject.dto.AdminLoginResponseDto;
import rw.rutakayile.k8sproject.dto.ApiResponse;
import rw.rutakayile.k8sproject.dto.CreateAdminDto;
import rw.rutakayile.k8sproject.model.Admin;
import rw.rutakayile.k8sproject.service.AdminService;
import rw.rutakayile.k8sproject.util.ErrorUtil;
import rw.rutakayile.k8sproject.util.JwtUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/admin/sign-up")
    public ResponseEntity<ApiResponse<AdminLoginResponseDto>> createAdmin(@RequestBody @Valid CreateAdminDto dto, BindingResult bindingResult) {
        ErrorUtil.checkForErrors(bindingResult);
        Admin admin = adminService.createAdmin(dto);

        final String jwt =
                jwtTokenUtil.generateToken(admin.getEmail(), admin); // generates token

        AdminLoginResponseDto responseDto = new AdminLoginResponseDto();
        responseDto.setAdmin(admin);
        responseDto.setToken(jwt);

        ApiResponse<AdminLoginResponseDto> body =
                new ApiResponse<>(HttpStatus.OK, "Agents Retrieved Successfully", responseDto);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
