package com.gestionapprovisionnements.smartshop.controller;

import com.gestionapprovisionnements.smartshop.dto.Product.Request.ProductRequest;
import com.gestionapprovisionnements.smartshop.dto.Product.Request.ProductUpdatRequest;
import com.gestionapprovisionnements.smartshop.dto.Product.Response.ProductResponse;
import com.gestionapprovisionnements.smartshop.exiption.UnauthorizedAccessException;
import com.gestionapprovisionnements.smartshop.servec.ProductService;
import com.gestionapprovisionnements.smartshop.utils.UtilSession;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest request , HttpSession httpSession) {
        if(!UtilSession.isAdmin(httpSession)){
            throw  new UnauthorizedAccessException("you must be admin to access this resourcce ") ;
        }
        ProductResponse response = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @RequestBody ProductUpdatRequest request,
            HttpSession httpSession) {

        if(!UtilSession.isAdmin(httpSession)){
            throw new UnauthorizedAccessException("you must be admin to access this resourcce " ) ;
        }

        ProductResponse response = productService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpSession httpSession) {
        if(!UtilSession.isAdmin(httpSession)){
            throw new UnauthorizedAccessException("you must be admin to access this resourcce " ) ;
        }

        productService.delete(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id, HttpSession httpSession) {
        if(!UtilSession.isAdmin(httpSession)){
            throw new UnauthorizedAccessException("you must be admin to access this resourcce " ) ;
        }

        ProductResponse response = productService.get(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession httpSession) {

        if(!UtilSession.isAdmin(httpSession)){
            throw new UnauthorizedAccessException("you must be admin to access this resourcce " ) ;
        }

        Page<ProductResponse> response = productService.getAll(page, size);
        return ResponseEntity.ok(response);
    }
}
