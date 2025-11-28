package com.gestionapprovisionnements.smartshop.controller;

import com.gestionapprovisionnements.smartshop.dto.Client.Request.ClientRequest;
import com.gestionapprovisionnements.smartshop.dto.Client.Response.ClientResponse;
import com.gestionapprovisionnements.smartshop.servec.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid ClientRequest request) {
        ClientResponse response = clientService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid ClientRequest request) {

        ClientResponse response = clientService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.ok("Client deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAll() {
        return ResponseEntity.ok(clientService.getAll());
    }
}
