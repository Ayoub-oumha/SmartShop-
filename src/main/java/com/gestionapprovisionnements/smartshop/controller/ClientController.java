package com.gestionapprovisionnements.smartshop.controller;

import com.gestionapprovisionnements.smartshop.dto.Client.Request.ClientUpdatRequest;
import com.gestionapprovisionnements.smartshop.dto.Client.Response.ClientResponse;
import com.gestionapprovisionnements.smartshop.dto.Client.Response.ClientStatisticsResponse;
import com.gestionapprovisionnements.smartshop.dto.Order.response.OrderResponse;
import com.gestionapprovisionnements.smartshop.exiption.UnauthorizedAccessException;
import com.gestionapprovisionnements.smartshop.servec.ClientService;
import com.gestionapprovisionnements.smartshop.utils.UtilSession;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponse> update(
            @PathVariable Long id,
            @RequestBody ClientUpdatRequest request,
            HttpSession httpSession) {

        if (!UtilSession.isAdmin(httpSession)) {
            throw new UnauthorizedAccessException("You must be admin");
        }

        ClientResponse response = clientService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id , HttpSession httpSession) {
        if(!UtilSession.isAdmin(httpSession)){
            throw new UnauthorizedAccessException("You must be admin");
        }
        clientService.delete(id);
        return ResponseEntity.ok("Client deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> get(@PathVariable Long id , HttpSession httpSession) {
        if(!UtilSession.isAdmin(httpSession)){
            throw new UnauthorizedAccessException("You must be admin");
        }
        return ResponseEntity.ok(clientService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAll(HttpSession httpSession) {

        if (!UtilSession.isAdmin( httpSession)) {
            throw new UnauthorizedAccessException("You must be admin");
        }
        return ResponseEntity.ok(clientService.getAll());
    }

    @GetMapping("/me/profile")
    public ResponseEntity<ClientResponse> getMyProfile(HttpSession httpSession) {
        if(!UtilSession.isClient(httpSession)){
            throw new UnauthorizedAccessException("you must me authenticated and be clinet") ;
        }
        Long clientId = (Long) httpSession.getAttribute("userId");
        return ResponseEntity.ok(clientService.getMyProfile(clientId));
    }

    @GetMapping("/me/orders")
    public ResponseEntity<List<OrderResponse>> getMyOrderHistory(HttpSession httpSession) {
        if(!UtilSession.isClient(httpSession)){
            throw new UnauthorizedAccessException("you must me authenticated and be clinet") ;
        }
        Long clientId = (Long) httpSession.getAttribute("userId");
        return ResponseEntity.ok(clientService.getMyOrderHistory(clientId));
    }

    @GetMapping("/me/statistics")
    public ResponseEntity<ClientStatisticsResponse> getMyStatistics(HttpSession httpSession) {
        if(!UtilSession.isClient(httpSession)){
            throw new UnauthorizedAccessException("you must me authenticated and be clinet") ;
        }
        Long clientId = (Long) httpSession.getAttribute("userId");
        return ResponseEntity.ok(clientService.getMyStatistics(clientId));
    }
}
