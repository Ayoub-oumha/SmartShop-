package com.gestionapprovisionnements.smartshop.servec;

import com.gestionapprovisionnements.smartshop.dto.Product.Request.ProductRequest;
import com.gestionapprovisionnements.smartshop.dto.Product.Response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);
    ProductResponse get(Long id);
    List<ProductResponse> getAll();
}

