package com.gestionapprovisionnements.smartshop.servec.Impl;

import com.gestionapprovisionnements.smartshop.dto.Promo.response.PromoCodeResponseDTO;
import com.gestionapprovisionnements.smartshop.entity.CodePromo;
import com.gestionapprovisionnements.smartshop.mapper.PromoCodeMapper;
import com.gestionapprovisionnements.smartshop.repository.CodePromoRepository;
import com.gestionapprovisionnements.smartshop.servec.PromoCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService {

    private final CodePromoRepository codePromoRepository;
    private final PromoCodeMapper promoCodeMapper;

    private static final String PREFIX = "PROMO-";
    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int CODE_SUFFIX_LENGTH = 4;
    private static final double DEFAULT_DISCOUNT = 5.0; // 5%
    private static final int DEFAULT_VALID_DAYS = 30;

    @Override
    public PromoCodeResponseDTO generatePromoCode() {
        String code = generateUniqueCode();

        LocalDateTime now = LocalDateTime.now();
        CodePromo cp = CodePromo.builder()
                .code(code)
                .pourcentageRemise(DEFAULT_DISCOUNT)
                .dateDebut(now)
                .dateFin(now.plusDays(DEFAULT_VALID_DAYS))
                .actif(true)
                .build();

        CodePromo saved = codePromoRepository.save(cp);
        return promoCodeMapper.toDto(saved);
    }

    @Override
    public Page<PromoCodeResponseDTO> getAllPromoCodes(Pageable pageable) {
        return codePromoRepository.findAll(pageable).map(promoCodeMapper::toDto);
    }

    private String generateUniqueCode() {
        for (int attempt = 0; attempt < 50; attempt++) {
            String suffix = randomSuffix();
            String candidate = (PREFIX + suffix).toUpperCase(Locale.ROOT);
            if (!codePromoRepository.findByCode(candidate).isPresent()) {
                return candidate;
            }
        }
        // fallback: use timestamp to guarantee uniqueness
        String fallback = PREFIX + System.currentTimeMillis();
        return fallback;
    }

    private String randomSuffix() {
        StringBuilder sb = new StringBuilder(CODE_SUFFIX_LENGTH);
        for (int i = 0; i < CODE_SUFFIX_LENGTH; i++) {
            int idx = RANDOM.nextInt(ALPHANUM.length());
            sb.append(ALPHANUM.charAt(idx));
        }
        return sb.toString();
    }
}

