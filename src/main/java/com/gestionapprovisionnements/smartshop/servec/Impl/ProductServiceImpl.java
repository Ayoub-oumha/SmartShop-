package com.gestionapprovisionnements.smartshop.servec.Impl;

import com.gestionapprovisionnements.smartshop.dto.Product.Request.ProductRequest;
import com.gestionapprovisionnements.smartshop.dto.Product.Request.ProductUpdatRequest;
import com.gestionapprovisionnements.smartshop.dto.Product.Response.ProductResponse;
import com.gestionapprovisionnements.smartshop.entity.Product;
import com.gestionapprovisionnements.smartshop.exiption.NotFoundException;
import com.gestionapprovisionnements.smartshop.exiption.ResourceAlreadyExistsException;
import com.gestionapprovisionnements.smartshop.mapper.ProductMapper;
import com.gestionapprovisionnements.smartshop.repository.ProductRepository;
import com.gestionapprovisionnements.smartshop.servec.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsProductByNameAndDeletedFalse(request.getName())) {
            throw new ResourceAlreadyExistsException("Un produit avec ce nom existe déjà");
        }

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .deleted(false)
                .build();

        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }
    @Override
    public ProductResponse update(Long id, ProductUpdatRequest request) {
        Optional<Product> opt = productRepository.findById(id);
        Product product = opt.filter(p -> p.getDeleted() == null || !p.getDeleted())
                .orElseThrow(() -> new NotFoundException("Produit non trouvé"));


        productMapper.updateFromDto(request, product);

        Product updated = productRepository.save(product);
        return productMapper.toResponse(updated);
    }
    @Override
    public void delete(Long id) {
        Optional<Product> opt = productRepository.findById(id);
        Product product = opt.filter(p -> p.getDeleted() == null || !p.getDeleted())
                .orElseThrow(() -> new NotFoundException("Produit non trouvé"));

        product.setDeleted(true);
        productRepository.save(product);
    }

    public ProductResponse get(Long id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Produit non trouvé"));
        return productMapper.toResponse(product);
    }

    @Override
    public Page<ProductResponse> getAll(int page, int size) {
        Page<Product> products = productRepository.findAllByDeletedFalse(PageRequest.of(page, size));
        return products.map(productMapper::toResponse);
    }
}
