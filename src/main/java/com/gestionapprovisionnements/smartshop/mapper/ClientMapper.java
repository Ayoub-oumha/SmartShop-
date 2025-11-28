package com.gestionapprovisionnements.smartshop.mapper;

import com.gestionapprovisionnements.smartshop.dto.Client.Request.ClientRequest;
import com.gestionapprovisionnements.smartshop.dto.Client.Response.ClientResponse;
import com.gestionapprovisionnements.smartshop.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientRequest request);

    ClientResponse toResponse(Client client);
}