package com.zekaya.eccommerce.service.impl;

import com.zekaya.eccommerce.dto.ProductDto;
import com.zekaya.eccommerce.dto.Response;
import com.zekaya.eccommerce.entity.Category;
import com.zekaya.eccommerce.entity.Product;
import com.zekaya.eccommerce.exception.NotFoundException;
import com.zekaya.eccommerce.mapper.EntityDtoMapper;
import com.zekaya.eccommerce.repository.CategoryRepo;
import com.zekaya.eccommerce.repository.ProductRepo;
import com.zekaya.eccommerce.service.AwsS3Service;
import com.zekaya.eccommerce.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Collectors;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;
    private final AwsS3Service awsS3Service;



    @Override
    public Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found"));
        String productImageUrl = awsS3Service.saveImageToS3(image);

        Product product = new Product();
        product.setCategory(category);
        product.setPrice(price);
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(productImageUrl);

        productRepo.save(product);
        return Response.builder()
                .status(200)
                .message("Product successfully created")
                .build();

    }

    @Override
    public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("Product not found"));
        Category category = null;
        String productImageUrl = null;
        if(categoryId != null){
            category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found"));
        }
        if (image != null || !image.isEmpty()){
            productImageUrl = awsS3Service.saveImageToS3(image);
        }
        if (category !=null) product.setCategory(category);
        if (name !=null) product.setName(name);
        if (price !=null) product.setPrice(price);
        if (description !=null) product.setDescription(description);
        if (productImageUrl !=null) product.setImageUrl(productImageUrl);

        productRepo.save(product);
        return Response.builder()
                .status(200)
                .message("Product updated successfully")
                .build();
    }

    @Override
    public Response deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("Product not found"));
        productRepo.delete(product);
        return Response.builder()
                .status(200)
                .message("Product deleted successfully")
                .build();
    }

    @Override
    public Response getProductById(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("Product not found"));
        ProductDto productDto = entityDtoMapper.productToProductDto(product);
        return Response.builder()
                .status(200)
                .message(String.valueOf(productDto))
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<ProductDto> productList;
        productList = productRepo.findAll(Sort.by(Sort.Direction.DESC,"id"))
                .stream()
                .map(entityDtoMapper::productToProductDto)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .message(String.valueOf(productList))
                .build();
    }

    @Override
    public Response getProductByCategory(Long categoryId) {
        List<Product> products = productRepo.findByCategoryId(categoryId);
        if(products.isEmpty()){
            throw new NotFoundException("No products found for this category");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::productToProductDto)
                .collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .productlist(productDtoList)
                .build();
    }

    @Override
    public Response searchProduct(String searchValue) {
        List<Product> products = productRepo.findByNameOrDescriptionContaining(searchValue, searchValue);

        if(products.isEmpty()){
            throw new NotFoundException("No products found");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::productToProductDto)
                .collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .productlist(productDtoList)
                .build();
    }
}
