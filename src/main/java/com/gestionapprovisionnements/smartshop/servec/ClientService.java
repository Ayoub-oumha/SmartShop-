package com.gestionapprovisionnements.smartshop.servec;

import com.gestionapprovisionnements.smartshop.dto.Client.Request.ClientRequest;
import com.gestionapprovisionnements.smartshop.dto.Client.Response.ClientResponse;


import java.util.List;

public interface ClientService {
    public ClientResponse create(ClientRequest request);
    public ClientResponse update(Long id, ClientRequest request);
    public void delete(Long id);
    public ClientResponse get(Long id);
    public List<ClientResponse> getAll();
}
