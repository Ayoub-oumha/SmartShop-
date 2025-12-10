package com.gestionapprovisionnements.smartshop.servec;

import com.gestionapprovisionnements.smartshop.dto.Payment.request.PaymentRequest;
import com.gestionapprovisionnements.smartshop.dto.Payment.request.PaymentUpdateStatusDTO;
import com.gestionapprovisionnements.smartshop.dto.Payment.response.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    PaymentResponseDTO addPayment(PaymentRequest dto);
    PaymentResponseDTO updatePaymentStatus(Long paymentId, PaymentUpdateStatusDTO dto);
    PaymentResponseDTO getPaymentById(Long id);
    List<PaymentResponseDTO> getPaymentsByOrderId(Long orderId);
    List<PaymentResponseDTO> getAllPayments();
}

