package com.gestionapprovisionnements.smartshop.servec.Impl;

import com.gestionapprovisionnements.smartshop.dto.Client.Request.ClientUpdatRequest;
import com.gestionapprovisionnements.smartshop.dto.Client.Response.ClientResponse;
import com.gestionapprovisionnements.smartshop.dto.Client.Response.ClientStatisticsResponse;
import com.gestionapprovisionnements.smartshop.dto.Order.response.OrderResponse;
import com.gestionapprovisionnements.smartshop.entity.Client;
import com.gestionapprovisionnements.smartshop.entity.Order;
import com.gestionapprovisionnements.smartshop.entity.Payment;
import com.gestionapprovisionnements.smartshop.entity.enums.PaymentStatus;
import com.gestionapprovisionnements.smartshop.exiption.NotFoundException;
import com.gestionapprovisionnements.smartshop.mapper.ClientMapper;
import com.gestionapprovisionnements.smartshop.mapper.OrderMapper;
import com.gestionapprovisionnements.smartshop.repository.ClientRepository;
import com.gestionapprovisionnements.smartshop.repository.PaymentRepository;
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
    private final OrderMapper orderMapper;
    private final PaymentRepository paymentRepository ;

    @Override
    @Transactional
    public ClientResponse update(Long id, ClientUpdatRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request must not be null");
        }

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));


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

    @Override
    public ClientResponse getMyProfile(Long userId) {
        Client client = clientRepository.findByUser_Id(userId)
                .orElseThrow(() -> new NotFoundException("Client not found"));
        return clientMapper.toResponse(client);
    }

    @Override
    public List<OrderResponse> getMyOrderHistory(Long userId) {
        Client client = clientRepository.findByUser_Id(userId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        return client.getOrders().stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClientStatisticsResponse getMyStatistics(Long userId) {
        Client client = clientRepository.findByUser_Id(userId)
                .orElseThrow(() -> new NotFoundException("Client not found"));
        Long    numberOfcommande = client.getOrders().stream().count() ;
        Double  montantCumule = client.getOrders().stream()
                .map(Order::getId)
                .flatMap(orderdId -> paymentRepository.findByOrderId(orderdId).stream() )
                .mapToDouble(Payment::getMontant)
                .sum() ;
        ClientStatisticsResponse clientStatisticsResponse = ClientStatisticsResponse.builder().nombreCommandes(numberOfcommande)
                .montantCumule(montantCumule).build() ;
        return clientStatisticsResponse ;
    }
}
