package com.gestionapprovisionnements.smartshop.servec.Impl;

import com.gestionapprovisionnements.smartshop.dto.Order.request.OrderItemRequest;
import com.gestionapprovisionnements.smartshop.dto.Order.request.OrderRequest;
import com.gestionapprovisionnements.smartshop.dto.Order.response.OrderResponse;
import com.gestionapprovisionnements.smartshop.entity.*;
import com.gestionapprovisionnements.smartshop.entity.*;
import com.gestionapprovisionnements.smartshop.entity.enums.CustomerTier;
import com.gestionapprovisionnements.smartshop.entity.enums.OrderStatus;
import com.gestionapprovisionnements.smartshop.exiption.BusinessException;
import com.gestionapprovisionnements.smartshop.exiption.NotFoundException;
import com.gestionapprovisionnements.smartshop.mapper.OrderMapper;
import com.gestionapprovisionnements.smartshop.mapper.PaymentMapper;
import com.gestionapprovisionnements.smartshop.repository.CodePromoRepository;
import com.gestionapprovisionnements.smartshop.repository.ClientRepository;
import com.gestionapprovisionnements.smartshop.repository.OrderRepository;
import com.gestionapprovisionnements.smartshop.repository.PaymentRepository;
import com.gestionapprovisionnements.smartshop.repository.ProductRepository;
import com.gestionapprovisionnements.smartshop.servec.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CodePromoRepository codePromoRepository;

    private final OrderMapper orderMapper;


    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderReauest) {
        if (orderReauest == null || orderReauest.getClientId() == null || orderReauest.getItems() == null || orderReauest.getItems().isEmpty()) {
            throw new IllegalArgumentException("Invalid order request");
        }

        Client client = clientRepository.findById(orderReauest.getClientId())
                .orElseThrow(() -> new NotFoundException("Client not found"));

        Order order = new Order();
        order.setClient(client);
        order.setDateCommande(LocalDateTime.now());
        order.setRemise(0.0);

        List<OrderItem> orderItems = new ArrayList<>();
        double subtotal = 0.0;
        boolean stockSufficient = true;
        String rejectionMessage = null;

        for (OrderItemRequest itemDto : orderReauest.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            int qty = itemDto.getQuantity() == null ? 0 : itemDto.getQuantity();
            if (qty <= 0) {
                stockSufficient = false;
                rejectionMessage = "Order is REJECTED, invalid quantity for product " + product.getName();
            }

            if (qty > product.getStock()) {
                stockSufficient = false;
                rejectionMessage = "Order is REJECTED, Insufficient stock for product " + product.getName();
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(qty);
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setTotalLine(qty * product.getPrice());

            orderItems.add(orderItem);
            subtotal += orderItem.getTotalLine();
        }

        order.setItems(orderItems);
        order.setSousTotal(subtotal);

        // Apply loyalty discount bach n3rfo chhal n9so
        double loyaltyDiscount = calculateLoyaltyDiscount(client, subtotal);

        // promo code discount
        double promoDiscount = 0.0;
        CodePromo promo = null;
        if (orderReauest.getPromoCode() != null && !orderReauest.getPromoCode().trim().isEmpty()) {
            Optional<CodePromo> opt = codePromoRepository.findByCode(orderReauest.getPromoCode().trim());
            if (opt.isPresent()) {
                CodePromo code = opt.get();
                LocalDateTime now = LocalDateTime.now();
                if (Boolean.TRUE.equals(code.getActif()) && (code.getDateDebut().isBefore(now) || code.getDateDebut().isEqual(now))
                        && (code.getDateFin().isAfter(now) || code.getDateFin().isEqual(now))) {
                    promo = code;
                    promoDiscount = Math.round(subtotal * (code.getPourcentageRemise() / 100.0) * 100.0) / 100.0;
                }
            }
        }

        double totalDiscount = loyaltyDiscount + promoDiscount;
        order.setRemise(totalDiscount);

        // Total calculations
        double amountAfterDiscount = subtotal - totalDiscount;
        // 20%
        double tvaRate = 0.2;
        double tva = Math.round(amountAfterDiscount * tvaRate * 100.0) / 100.0;
        order.setTva(tva);
        double total = Math.round((amountAfterDiscount + tva) * 100.0) / 100.0;
        order.setTotal(total);
        order.setMontantRestant(total);
        order.setStatut(stockSufficient ? OrderStatus.PENDING : OrderStatus.REJECTED);

        if (promo != null) {
            order.setCodePromo(promo.getCode());
        }

        Order saved = orderRepository.save(order);

        // Build response using mapper
        OrderResponse response = orderMapper.toResponse(saved);
        if (!stockSufficient) {
            response.setMessage(rejectionMessage);
        }

        return response;
    }

    @Override
    public OrderResponse get(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        return orderMapper.toResponse(order);
    }

    @Override
    public Page<OrderResponse> getAll(int page, int size) {
        Page<Order> pageOrders = orderRepository.findAll(PageRequest.of(page, size));
        return pageOrders.map(orderMapper::toResponse);
    }

    @Override
    public List<OrderResponse> getByClient(Long clientId) {
        List<Order> orders = orderRepository.findByClientId(clientId);
        List<OrderResponse> responses = new ArrayList<>();
        for (Order o : orders) {
            responses.add(orderMapper.toResponse(o));
        }
        return responses;
    }

    @Override
    @Transactional
    public OrderResponse confirmOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        if (order.getStatut() != OrderStatus.PENDING) {
            throw new BusinessException("Only PENDING orders can be confirmed");
        }
        if (order.getMontantRestant() > 0) {
            throw new BusinessException("Order is not fully paid");
        }

        for (OrderItem orderItem : order.getItems()) {
            Product p = orderItem.getProduct();
            if (orderItem.getQuantity() > p.getStock()) {
                order.setStatut(OrderStatus.REJECTED);
                orderRepository.save(order);
                throw new BusinessException("Insufficient stock for product " + p.getName());
            }
        }

        for (OrderItem oi : order.getItems()) {
            Product p = oi.getProduct();
            p.setStock(p.getStock() - oi.getQuantity());
            productRepository.save(p);
        }

        if (order.getCodePromo() != null) {
            Optional<CodePromo> codeProm = codePromoRepository.findByCode(order.getCodePromo());
            codeProm.ifPresent(codePromo -> { codePromo.setActif(false); codePromoRepository.save(codePromo); });
        }

        order.setStatut(OrderStatus.CONFIRMED);
        Order saved = orderRepository.save(order);

        updateClientStats(saved.getClient(), saved.getTotal());

        return orderMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        if (order.getStatut() != OrderStatus.PENDING) {
            throw new BusinessException("Only PENDING orders can be canceled");
        }
        order.setStatut(OrderStatus.CANCELED);
        Order saved = orderRepository.save(order);
        return orderMapper.toResponse(saved);
    }

    private double calculateLoyaltyDiscount(Client client, double subtotal) {
        switch (client.getCustomerTier()) {
            case SILVER:
                return subtotal >= 500 ? Math.round(subtotal * 0.05 * 100.0) / 100.0 : 0.0;
            case GOLD:
                return subtotal >= 800 ? Math.round(subtotal * 0.10 * 100.0) / 100.0 : 0.0;
            case PLATINUM:
                return subtotal >= 1200 ? Math.round(subtotal * 0.15 * 100.0) / 100.0 : 0.0;
            default:
                return 0.0;
        }
    }

    private void updateClientStats(Client client, double orderTotal) {
        client.setTotalOrders(client.getTotalOrders() + 1);
        client.setTotalSpent(client.getTotalSpent() + orderTotal);
        if (client.getFirstOrderDate() == null) {
            client.setFirstOrderDate(LocalDateTime.now());
        }
        client.setLastOrderDate(LocalDateTime.now());
        updateClientTier(client);
        clientRepository.save(client);
    }

    private void updateClientTier(Client client) {
        int orders = client.getTotalOrders();
        double spent = client.getTotalSpent();
        if (orders >= 20 || spent >= 15000) {
            client.setCustomerTier(CustomerTier.PLATINUM);
        } else if (orders >= 10 || spent >= 5000) {
            client.setCustomerTier(CustomerTier.GOLD);
        } else if (orders >= 3 || spent >= 1000) {
            client.setCustomerTier(CustomerTier.SILVER);
        }
    }

}
