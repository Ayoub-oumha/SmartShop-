package com.gestionapprovisionnements.smartshop.servec;

import com.gestionapprovisionnements.smartshop.dto.Promo.response.PromoCodeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromoCodeService {
    PromoCodeResponseDTO generatePromoCode();
    Page<PromoCodeResponseDTO> getAllPromoCodes(Pageable pageable);
}

