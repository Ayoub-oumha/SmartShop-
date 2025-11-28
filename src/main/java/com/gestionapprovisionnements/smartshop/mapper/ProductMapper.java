package com.gestionapprovisionnements.smartshop.mapper;

import com.gestionapprovisionnements.smartshop.dto.Product.Request.ProductRequest;
import com.gestionapprovisionnements.smartshop.dto.Product.Response.ProductResponse;
import com.gestionapprovisionnements.smartshop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Product toEntity(ProductRequest request);

    ProductResponse toResponse(Product product);
}

