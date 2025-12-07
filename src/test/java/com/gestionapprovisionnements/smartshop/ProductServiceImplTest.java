package com.gestionapprovisionnements.smartshop;

import com.gestionapprovisionnements.smartshop.dto.Product.Request.ProductRequest;
import com.gestionapprovisionnements.smartshop.dto.Product.Request.ProductUpdatRequest;
import com.gestionapprovisionnements.smartshop.dto.Product.Response.ProductResponse;
import com.gestionapprovisionnements.smartshop.entity.Product;
import com.gestionapprovisionnements.smartshop.exiption.NotFoundException;
import com.gestionapprovisionnements.smartshop.exiption.ResourceAlreadyExistsException;
import com.gestionapprovisionnements.smartshop.mapper.ProductMapper;
import com.gestionapprovisionnements.smartshop.repository.ProductRepository;
import com.gestionapprovisionnements.smartshop.servec.Impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductRequest productRequest;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(5000.0)
                .stock(10)
                .deleted(false)
                .build();

        productRequest = new ProductRequest();
        productRequest.setName("Laptop");
        productRequest.setPrice(5000.0);
        productRequest.setStock(10);

        productResponse = new ProductResponse();
        productResponse.setId(1L);
        productResponse.setName("Laptop");
        productResponse.setPrice(5000.0);
        productResponse.setStock(10);
    }

    @Test
    void create_ShouldCreateProduct_WhenValidRequest() {
        when(productRepository.existsProductByNameAndDeletedFalse(productRequest.getName())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.create(productRequest);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(5000.0, result.getPrice());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void create_ShouldThrowException_WhenProductAlreadyExists() {
        when(productRepository.existsProductByNameAndDeletedFalse(productRequest.getName())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> productService.create(productRequest));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void update_ShouldUpdateProduct_WhenProductExists() {
        ProductUpdatRequest updateRequest = new ProductUpdatRequest();
        updateRequest.setPrice(6000.0);
        updateRequest.setStock(15);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.update(1L, updateRequest);

        assertNotNull(result);
        verify(productMapper).updateFromDto(updateRequest, product);
        verify(productRepository).save(product);
    }

    @Test
    void update_ShouldThrowException_WhenProductNotFound() {
        ProductUpdatRequest updateRequest = new ProductUpdatRequest();
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.update(1L, updateRequest));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void delete_ShouldSoftDeleteProduct_WhenProductExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        productService.delete(1L);

        assertTrue(product.getDeleted());
        verify(productRepository).save(product);
    }

    @Test
    void delete_ShouldThrowException_WhenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.delete(1L));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void get_ShouldReturnProduct_WhenProductExists() {
        when(productRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(product));
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());
    }

    @Test
    void get_ShouldThrowException_WhenProductNotFound() {
        when(productRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.get(1L));
    }

    @Test
    void getAll_ShouldReturnPageOfProducts() {
        Product product2 = Product.builder()
                .id(2L)
                .name("Mouse")
                .price(100.0)
                .stock(50)
                .deleted(false)
                .build();

        Page<Product> productPage = new PageImpl<>(Arrays.asList(product, product2));
        when(productRepository.findAllByDeletedFalse(PageRequest.of(0, 10))).thenReturn(productPage);
        when(productMapper.toResponse(any(Product.class))).thenReturn(productResponse);

        Page<ProductResponse> result = productService.getAll(0, 10);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(productRepository).findAllByDeletedFalse(PageRequest.of(0, 10));
    }
}
