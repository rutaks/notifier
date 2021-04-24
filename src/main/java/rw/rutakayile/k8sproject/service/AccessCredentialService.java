package rw.rutakayile.k8sproject.service;

import rw.rutakayile.k8sproject.dto.AccessCredentialDto;
import rw.rutakayile.k8sproject.model.AccessCredential;

import java.util.List;

public interface AccessCredentialService {
    AccessCredential createAccessCredential(AccessCredentialDto accessCredentialDto);
    void disableAccessCredential(String apiKey);
    AccessCredential findByApiKey(String apiKey);
    List<AccessCredential> findAll();
}
