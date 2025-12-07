package com.gestionapprovisionnements.smartshop.controller;

import com.gestionapprovisionnements.smartshop.dto.Order.request.OrderRequest;
import com.gestionapprovisionnements.smartshop.dto.Order.response.OrderResponse;
import com.gestionapprovisionnements.smartshop.servec.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        OrderResponse response = orderService.get(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OrderResponse> orders = orderService.getAll(page, size);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByClient(@PathVariable Long clientId) {
        List<OrderResponse> orders = orderService.getByClient(clientId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<OrderResponse> confirmOrder(@PathVariable Long id) {
        OrderResponse response = orderService.confirmOrder(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id) {
        OrderResponse response = orderService.cancelOrder(id);
        return ResponseEntity.ok(response);
    }
}
