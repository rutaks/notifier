package rw.rutakayile.k8sproject.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.rutakayile.k8sproject.dto.AccessCredentialDto;
import rw.rutakayile.k8sproject.exception.EntityNotFoundException;
import rw.rutakayile.k8sproject.model.AccessCredential;
import rw.rutakayile.k8sproject.model.Client;
import rw.rutakayile.k8sproject.repository.AccessCredentialRepository;
import rw.rutakayile.k8sproject.service.AccessCredentialService;
import rw.rutakayile.k8sproject.service.ClientService;
import rw.rutakayile.k8sproject.util.StringUtil;

import java.util.List;

@Service
public class AccessCredentialServiceImpl implements AccessCredentialService {

    @Autowired
    private AccessCredentialRepository accessCredentialRepo;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AccessCredential createAccessCredential(AccessCredentialDto accessCredentialDto) {
        Client client = clientService.findClientById(accessCredentialDto.getClientId());
        AccessCredential accessCredential = new AccessCredential();
        StringBuilder apiKey = StringUtil.generateRandomString(20);

        accessCredential.setApiKey(apiKey.toString());
        accessCredential.setClient(client);

        return accessCredentialRepo.save(accessCredential);
    }

    @Override
    public void disableAccessCredential(String apiKey) {
        AccessCredential accessCredential = findByApiKey(apiKey);
        accessCredential.setDeleted(true);
        accessCredentialRepo.save(accessCredential);
    }

    @Override
    public AccessCredential findByApiKey(String apiKey) {
        return accessCredentialRepo.findByApiKey(apiKey);
    }

    @Override
    public List<AccessCredential> findAll() {
        return accessCredentialRepo.findAll();
    }
}
