package com.gestionapprovisionnements.smartshop.servec;

import com.gestionapprovisionnements.smartshop.dto.Product.Request.ProductRequest;
import com.gestionapprovisionnements.smartshop.dto.Product.Request.ProductUpdatRequest;
import com.gestionapprovisionnements.smartshop.dto.Product.Response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse update(Long id, ProductUpdatRequest request);
    void delete(Long id);
    ProductResponse get(Long id);
    Page<ProductResponse> getAll(int page, int size);
//    List<ProductResponse> getAll();
}
