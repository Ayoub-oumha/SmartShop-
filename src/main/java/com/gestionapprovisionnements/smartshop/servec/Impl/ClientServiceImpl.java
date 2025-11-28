package com.gestionapprovisionnements.smartshop.servec.Impl;

import com.gestionapprovisionnements.smartshop.dto.Client.Request.ClientRequest;
import com.gestionapprovisionnements.smartshop.dto.Client.Response.ClientResponse;
import com.gestionapprovisionnements.smartshop.entity.Client;
import com.gestionapprovisionnements.smartshop.entity.enums.CustomerTier;
import com.gestionapprovisionnements.smartshop.mapper.ClientMapper;
import com.gestionapprovisionnements.smartshop.repository.ClientRepository;
import com.gestionapprovisionnements.smartshop.servec.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponse create(ClientRequest request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already taken");
        }

        Client client = Client.builder()
                .nom(request.getNom())
                .email(request.getEmail())
                .tier(CustomerTier.BASIC)
                .build();

        Client saved = clientRepository.save(client);
        return clientMapper.toResponse(saved);
    }

    @Override
    public ClientResponse update(Long id, ClientRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setNom(request.getNom());
        client.setEmail(request.getEmail());

        Client updated = clientRepository.save(client);
        return clientMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found");
        }
        clientRepository.deleteById(id);
    }

    @Override
    public ClientResponse get(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return clientMapper.toResponse(client);
    }

    @Override
    public List<ClientResponse> getAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toResponse)
                .collect(Collectors.toList());
    }
}
