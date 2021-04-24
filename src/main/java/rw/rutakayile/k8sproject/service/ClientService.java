package rw.rutakayile.k8sproject.service;

import rw.rutakayile.k8sproject.dto.ClientDto;
import rw.rutakayile.k8sproject.model.Client;

public interface ClientService {
    Client createClient(ClientDto clientDto);
    Client disableClient(Long id);
    Client findClientById(Long id);
    Client modifyClient(Long id, ClientDto dto);
}
