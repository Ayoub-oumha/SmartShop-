package com.gestionapprovisionnements.smartshop.controller;

import com.gestionapprovisionnements.smartshop.dto.Promo.response.PromoCodeResponseDTO;
import com.gestionapprovisionnements.smartshop.exiption.UnauthorizedAccessException;
import com.gestionapprovisionnements.smartshop.servec.PromoCodeService;
import com.gestionapprovisionnements.smartshop.utils.UtilSession;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promocode")
@RequiredArgsConstructor
public class PromoCode {

    public final PromoCodeService  promoCodeService;

    @GetMapping("getAll")
    public ResponseEntity<Page<PromoCodeResponseDTO>> getAll(@RequestParam(defaultValue = "0" ) int page , @RequestParam(defaultValue = "10" ) int size , HttpSession httpSession) {
        if(!UtilSession.isAdmin(httpSession)) {
            throw new UnauthorizedAccessException("you must be admin ") ;
        }
        Page<PromoCodeResponseDTO> response = promoCodeService.getAllPromoCodes(page , size) ;
        return ResponseEntity.ok(response) ;
    }

    @GetMapping("/generatecode")
    public ResponseEntity<PromoCodeResponseDTO> generatePromoCode(HttpSession httpSession){
        if(!UtilSession.isAdmin(httpSession)) {
            throw new UnauthorizedAccessException("you must be admin ") ;
        }
        return ResponseEntity.ok(promoCodeService.generatePromoCode());
    }
}
