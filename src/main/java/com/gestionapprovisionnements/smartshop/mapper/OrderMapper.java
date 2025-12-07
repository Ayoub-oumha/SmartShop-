package com.gestionapprovisionnements.smartshop.mapper;

import com.gestionapprovisionnements.smartshop.dto.Order.response.OrderItemResponse;
import com.gestionapprovisionnements.smartshop.dto.Order.response.OrderResponse;
import com.gestionapprovisionnements.smartshop.entity.Order;
import com.gestionapprovisionnements.smartshop.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "client.id", target = "clientId")
    OrderResponse toResponse(Order order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponse toItemResponse(OrderItem item);

    List<OrderItemResponse> toItemResponses(List<OrderItem> items);
}
