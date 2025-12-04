package com.gestionapprovisionnements.smartshop.servec.Impl;

import com.gestionapprovisionnements.smartshop.dto.Client.Request.ClientUpdatRequest;
import com.gestionapprovisionnements.smartshop.dto.Client.Response.ClientResponse;
import com.gestionapprovisionnements.smartshop.entity.Client;
import com.gestionapprovisionnements.smartshop.exiption.NotFoundException;
import com.gestionapprovisionnements.smartshop.mapper.ClientMapper;
import com.gestionapprovisionnements.smartshop.repository.ClientRepository;
import com.gestionapprovisionnements.smartshop.servec.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientResponse update(Long id, ClientUpdatRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request must not be null");
        }

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        // Mise à jour partielle via MapStruct : n'écrase que les champs non nuls du DTO
        clientMapper.updateFromDto(request, client);

        Client saved = clientRepository.save(client);
        return clientMapper.toResponse(saved);
    }

    @Override

    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new NotFoundException("Client not found");
        }
        clientRepository.deleteById(id);
    }

    @Override
    public ClientResponse get(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with this id  not found"));
        return clientMapper.toResponse(client);
    }

    @Override
    public List<ClientResponse> getAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toResponse)
                .collect(Collectors.toList());
    }
}
