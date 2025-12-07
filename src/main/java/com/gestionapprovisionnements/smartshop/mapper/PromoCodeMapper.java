package com.gestionapprovisionnements.smartshop.mapper;

import com.gestionapprovisionnements.smartshop.dto.Promo.response.PromoCodeResponseDTO;
import com.gestionapprovisionnements.smartshop.entity.CodePromo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PromoCodeMapper {
    PromoCodeResponseDTO toDto(CodePromo codePromo);
    List<PromoCodeResponseDTO> toDtos(List<CodePromo> codePromos);
}

