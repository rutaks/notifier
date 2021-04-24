package rw.rutakayile.k8sproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rw.rutakayile.k8sproject.dto.AdminLoginResponseDto;
import rw.rutakayile.k8sproject.dto.ApiResponse;
import rw.rutakayile.k8sproject.dto.ClientDto;
import rw.rutakayile.k8sproject.model.Client;
import rw.rutakayile.k8sproject.service.ClientService;
import rw.rutakayile.k8sproject.util.ErrorUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<Client>> getClient(@PathVariable Long id) {
        Client client = clientService.findClientById(id);

        ApiResponse<Client> body =
                new ApiResponse<>(HttpStatus.OK, "Client found successfully", client);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Client>> createClient(@RequestBody @Valid ClientDto dto, BindingResult bindingResult) {
        ErrorUtil.checkForErrors(bindingResult);
        Client client = clientService.createClient(dto);

        ApiResponse<Client> body =
                new ApiResponse<>(HttpStatus.OK, "Client created successfully", client);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Client>> disableClient(@PathVariable Long id) {
        Client client = clientService.disableClient(id);

        ApiResponse<Client> body =
                new ApiResponse<>(HttpStatus.OK, "Client disabled successfully", client);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<Client>> modifyClient(@PathVariable Long id, @RequestBody @Valid ClientDto dto, BindingResult bindingResult) {
        ErrorUtil.checkForErrors(bindingResult);
        Client client = clientService.modifyClient(id, dto);

        ApiResponse<Client> body =
                new ApiResponse<>(HttpStatus.OK, "Client modified successfully", client);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
