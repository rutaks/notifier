package rw.rutakayile.k8sproject.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.rutakayile.k8sproject.dto.ClientDto;
import rw.rutakayile.k8sproject.exception.EntityNotFoundException;
import rw.rutakayile.k8sproject.model.Client;
import rw.rutakayile.k8sproject.repository.ClientRepository;
import rw.rutakayile.k8sproject.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Client createClient(ClientDto clientDto) {
        Client client = modelMapper.map(clientDto, Client.class);
        return clientRepo.save(client);
    }

    @Override
    public Client disableClient(Long id) {
        Client client = findClientById(id);
        client.setDeleted(true);
        return clientRepo.save(client);
    }

    @Override
    public Client findClientById(Long id) {
        return clientRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }

    @Override
    public Client modifyClient(Long id, ClientDto dto) {
        Client client = findClientById(id);
        client.setNames(dto.getNames());
        return clientRepo.save(client);
    }
}
