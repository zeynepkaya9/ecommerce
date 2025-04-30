package com.zekaya.eccommerce.controller;


import com.zekaya.eccommerce.dto.Response;
import com.zekaya.eccommerce.exception.InvalidCredentialsException;
import com.zekaya.eccommerce.exception.NotFoundException;
import com.zekaya.eccommerce.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createProduct(
            @RequestParam Long categoryId,
            @RequestParam MultipartFile image,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price
    ){
        if (categoryId == null || image.isEmpty() || name.isEmpty() || description.isEmpty() || price == null){
            throw new InvalidCredentialsException("All fields are required");
        }

        return ResponseEntity.ok(productService.createProduct(categoryId, image, name, description, price));

    }
}
