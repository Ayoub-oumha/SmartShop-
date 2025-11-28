package com.gestionapprovisionnements.smartshop.servec.Impl;

import com.gestionapprovisionnements.smartshop.dto.Product.Request.ProductRequest;
import com.gestionapprovisionnements.smartshop.dto.Product.Response.ProductResponse;
import com.gestionapprovisionnements.smartshop.entity.Product;
import com.gestionapprovisionnements.smartshop.mapper.ProductMapper;
import com.gestionapprovisionnements.smartshop.repository.ProductRepository;
import com.gestionapprovisionnements.smartshop.servec.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsByNomAndDeletedFalse(request.getNom())) {
            throw new RuntimeException("Un produit avec ce nom existe déjà");
        }

        Product product = Product.builder()
                .nom(request.getNom())
                .prixUnitaire(request.getPrixUnitaire())
                .stockDisponible(request.getStockDisponible())
                .deleted(false)
                .build();

        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        product.setNom(request.getNom());
        product.setPrixUnitaire(request.getPrixUnitaire());
        product.setStockDisponible(request.getStockDisponible());

        Product updated = productRepository.save(product);
        return productMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        productRepository.delete(product);
    }

    @Override
    public ProductResponse get(Long id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }
}

