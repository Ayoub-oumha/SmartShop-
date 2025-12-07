package com.gestionapprovisionnements.smartshop.mapper;

import com.gestionapprovisionnements.smartshop.dto.Payment.request.PaymentCreateDTO;
import com.gestionapprovisionnements.smartshop.dto.Payment.request.PaymentUpdateStatusDTO;
import com.gestionapprovisionnements.smartshop.dto.Payment.response.PaymentResponseDTO;
import com.gestionapprovisionnements.smartshop.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "id", target = "id")
    PaymentResponseDTO toResponseDTO(Payment payment);

    List<PaymentResponseDTO> toResponseDTOs(List<Payment> payments);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroPaiement", ignore = true)
    @Mapping(target = "datePaiement", ignore = true)
    @Mapping(target = "statut", ignore = true)
    Payment toEntity(PaymentCreateDTO request);

    // no direct mapping for update status dto -> entity; handled in service
}
