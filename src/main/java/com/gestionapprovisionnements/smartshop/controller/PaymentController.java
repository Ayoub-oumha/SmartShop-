package com.gestionapprovisionnements.smartshop.controller;

import com.gestionapprovisionnements.smartshop.dto.Payment.request.PaymentRequest;
import com.gestionapprovisionnements.smartshop.dto.Payment.request.PaymentUpdateStatusDTO;
import com.gestionapprovisionnements.smartshop.dto.Payment.response.PaymentResponseDTO;
import com.gestionapprovisionnements.smartshop.exiption.UnauthorizedAccessException;
import com.gestionapprovisionnements.smartshop.servec.PaymentService;
import com.gestionapprovisionnements.smartshop.utils.UtilSession;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> addPayment(@Valid @RequestBody PaymentRequest request ,HttpSession httpSession) {
        if(!UtilSession.isAdmin(httpSession) ){
            throw new UnauthorizedAccessException("you must be authentified and admin") ;
        }
        PaymentResponseDTO response = paymentService.addPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PaymentResponseDTO> updatePaymentStatus(
            @PathVariable Long id,
            @Valid @RequestBody PaymentUpdateStatusDTO request) {
        PaymentResponseDTO response = paymentService.updatePaymentStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPayment(@PathVariable Long id) {
        PaymentResponseDTO response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByOrder(@PathVariable Long orderId) {
        List<PaymentResponseDTO> payments = paymentService.getPaymentsByOrderId(orderId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        List<PaymentResponseDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }
}
