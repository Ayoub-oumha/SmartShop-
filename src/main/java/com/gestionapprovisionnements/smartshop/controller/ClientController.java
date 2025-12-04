package com.gestionapprovisionnements.smartshop.controller;

import com.gestionapprovisionnements.smartshop.dto.Client.Request.ClientRequest;
import com.gestionapprovisionnements.smartshop.dto.Client.Request.ClientUpdatRequest;
import com.gestionapprovisionnements.smartshop.dto.Client.Response.ClientResponse;
import com.gestionapprovisionnements.smartshop.entity.enums.UserRole;
import com.gestionapprovisionnements.smartshop.exiption.UnauthorizedAccessException;
import com.gestionapprovisionnements.smartshop.servec.ClientService;
import com.gestionapprovisionnements.smartshop.utils.UtilSession;
import jakarta.servlet.http.HttpSession;
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
}
