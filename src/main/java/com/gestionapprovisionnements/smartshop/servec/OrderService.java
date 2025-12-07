package com.gestionapprovisionnements.smartshop.servec;

import com.gestionapprovisionnements.smartshop.dto.Order.request.OrderRequest;
import com.gestionapprovisionnements.smartshop.dto.Order.response.OrderResponse;
import com.gestionapprovisionnements.smartshop.dto.Payment.request.PaymentRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest dto);
    OrderResponse get(Long id);
    Page<OrderResponse> getAll(int page, int size);
    List<OrderResponse> getByClient(Long clientId);
    OrderResponse confirmOrder(Long id);
    OrderResponse cancelOrder(Long id);
}


