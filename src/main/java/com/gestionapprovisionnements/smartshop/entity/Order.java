package com.gestionapprovisionnements.smartshop.entity;

import com.gestionapprovisionnements.smartshop.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id",nullable = false)
    private Client client;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    
    @Column(nullable = false)
    private Double sousTotal;
    
    @Column(nullable = false)
    private Double remise = 0.0;
    
    @Column(nullable = false)
    private Double tva;
    
    @Column(nullable = false)
    private Double total;
    
    private String codePromo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus statut = OrderStatus.PENDING;
    
    @Column(nullable = false)
    private Double montantRestant;
    
    @Column(nullable = false)
    private LocalDateTime dateCommande = LocalDateTime.now();
}
