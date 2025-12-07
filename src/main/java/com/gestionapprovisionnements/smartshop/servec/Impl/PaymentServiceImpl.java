package com.gestionapprovisionnements.smartshop.servec.Impl;

import com.gestionapprovisionnements.smartshop.dto.Payment.request.PaymentCreateDTO;
import com.gestionapprovisionnements.smartshop.dto.Payment.request.PaymentUpdateStatusDTO;
import com.gestionapprovisionnements.smartshop.dto.Payment.response.PaymentResponseDTO;
import com.gestionapprovisionnements.smartshop.entity.Order;
import com.gestionapprovisionnements.smartshop.entity.Payment;
import com.gestionapprovisionnements.smartshop.entity.enums.OrderStatus;
import com.gestionapprovisionnements.smartshop.entity.enums.PaymentStatus;
import com.gestionapprovisionnements.smartshop.entity.enums.PaymentType;
import com.gestionapprovisionnements.smartshop.exiption.BusinessException;
import com.gestionapprovisionnements.smartshop.exiption.NotFoundException;
import com.gestionapprovisionnements.smartshop.mapper.PaymentMapper;
import com.gestionapprovisionnements.smartshop.repository.OrderRepository;
import com.gestionapprovisionnements.smartshop.repository.PaymentRepository;
import com.gestionapprovisionnements.smartshop.servec.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponseDTO addPayment(PaymentCreateDTO dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (order.getStatut() != OrderStatus.PENDING) {
            throw new BusinessException("Cannot add payment to non-PENDING order");
        }

        if (dto.getMontant() <= 0) {
            throw new BusinessException("Payment amount must be positive");
        }

        if (dto.getMontant() > order.getMontantRestant()) {
            throw new BusinessException("Payment amount exceeds remaining amount");
        }

        if (dto.getType() == PaymentType.ESPECES && dto.getMontant() > 20000) {
            throw new BusinessException("Cash payment cannot exceed 20,000 DH");
        }

        List<Payment> existingPayments = paymentRepository.findByOrderId(order.getId());
        int nextNumber = existingPayments.size() + 1;

        Payment payment = Payment.builder()
                .orderId(order.getId())
                .numeroPaiement(nextNumber)
                .montant(Math.round(dto.getMontant() * 100.0) / 100.0)
                .type(dto.getType())
                .datePaiement(LocalDateTime.now())
                .statut(dto.getType() == PaymentType.ESPECES ? PaymentStatus.ENCAISSE : PaymentStatus.EN_ATTENTE)
                .numeroCheque(dto.getNumeroCheque())
                .banque(dto.getBanque())
                .echeance(dto.getEcheance())
                .reference(dto.getReference())
                .build();

        if (payment.getStatut() == PaymentStatus.ENCAISSE) {
            payment.setDateEncaissement(LocalDateTime.now());
        }

        Payment saved = paymentRepository.save(payment);

        double newRemaining = Math.round((order.getMontantRestant() - payment.getMontant()) * 100.0) / 100.0;
        order.setMontantRestant(Math.max(0, newRemaining));
        orderRepository.save(order);

        return paymentMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public PaymentResponseDTO updatePaymentStatus(Long paymentId, PaymentUpdateStatusDTO dto) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        if (payment.getType() == PaymentType.ESPECES) {
            throw new BusinessException("Cannot change status of cash payment");
        }

        if (payment.getStatut() == dto.getStatut()) {
            throw new BusinessException("Payment already has this status");
        }

        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (payment.getStatut() == PaymentStatus.ENCAISSE && dto.getStatut() == PaymentStatus.REJETE) {
            double newRemaining = Math.round((order.getMontantRestant() + payment.getMontant()) * 100.0) / 100.0;
            order.setMontantRestant(newRemaining);
        }

        if (payment.getStatut() != PaymentStatus.ENCAISSE && dto.getStatut() == PaymentStatus.ENCAISSE) {
            payment.setDateEncaissement(LocalDateTime.now());
        }

        payment.setStatut(dto.getStatut());
        Payment saved = paymentRepository.save(payment);
        orderRepository.save(order);

        return paymentMapper.toResponseDTO(saved);
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found"));
        return paymentMapper.toResponseDTO(payment);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByOrderId(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new NotFoundException("Order not found");
        }
        List<Payment> payments = paymentRepository.findByOrderId(orderId);
        return payments.stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
