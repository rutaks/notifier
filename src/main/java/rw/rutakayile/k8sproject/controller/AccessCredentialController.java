package rw.rutakayile.k8sproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rw.rutakayile.k8sproject.dto.AccessCredentialDto;
import rw.rutakayile.k8sproject.dto.ApiResponse;
import rw.rutakayile.k8sproject.model.AccessCredential;
import rw.rutakayile.k8sproject.service.AccessCredentialService;
import rw.rutakayile.k8sproject.util.ErrorUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/access-credentials")
public class AccessCredentialController {
    @Autowired
    private AccessCredentialService accessCredentialService;

    @PostMapping
    private ResponseEntity<ApiResponse<AccessCredential>> createAccessCredential(@RequestBody @Valid AccessCredentialDto accessCredentialDto, BindingResult bindingResult) {
        ErrorUtil.checkForErrors(bindingResult);

        AccessCredential accessCredential = accessCredentialService.createAccessCredential(accessCredentialDto);

        ApiResponse<AccessCredential> body =
                new ApiResponse<>(HttpStatus.OK, "Access key created successfully", accessCredential);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping("{apiKey}")
    private ResponseEntity<ApiResponse<AccessCredential>> disableAccessCredential(@PathVariable String apiKey) {
        accessCredentialService.disableAccessCredential(apiKey);

        ApiResponse<AccessCredential> body =
                new ApiResponse<>(HttpStatus.OK, "Access key disabled successfully", null);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping("{apiKey}")
    private ResponseEntity<ApiResponse<AccessCredential>> findAccessCredential(@PathVariable String apiKey) {
        AccessCredential accessCredential = accessCredentialService.findByApiKey(apiKey);

        ApiResponse<AccessCredential> body =
                new ApiResponse<>(HttpStatus.OK, "Access key found successfully", null);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping
    private ResponseEntity<ApiResponse<List<AccessCredential>>> findAccessCredentials() {
        List<AccessCredential> accessCredentials = accessCredentialService.findAll();

        ApiResponse<List<AccessCredential>> body =
                new ApiResponse<>(HttpStatus.OK, "Access key found successfully", accessCredentials);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
