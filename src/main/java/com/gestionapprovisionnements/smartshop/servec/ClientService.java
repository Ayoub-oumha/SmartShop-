package com.gestionapprovisionnements.smartshop.servec;

import com.gestionapprovisionnements.smartshop.dto.Client.Request.ClientUpdatRequest;
import com.gestionapprovisionnements.smartshop.dto.Client.Response.ClientResponse;
import com.gestionapprovisionnements.smartshop.dto.Client.Response.ClientStatisticsResponse;
import com.gestionapprovisionnements.smartshop.dto.Order.response.OrderResponse;

import java.util.List;

public interface ClientService {

     ClientResponse update(Long id, ClientUpdatRequest request);
     void delete(Long id);
     ClientResponse get(Long id);
     List<ClientResponse> getAll();

     ClientResponse getMyProfile(Long clientId);
     List<OrderResponse> getMyOrderHistory(Long clientId);
     ClientStatisticsResponse getMyStatistics(Long clientId);
}
