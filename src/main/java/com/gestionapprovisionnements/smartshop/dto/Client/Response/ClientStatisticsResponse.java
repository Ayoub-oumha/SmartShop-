package com.gestionapprovisionnements.smartshop.dto.Client.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientStatisticsResponse {
    private Long nombreCommandes;
    private Double montantCumule;
}
