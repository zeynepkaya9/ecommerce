package com.zekaya.eccommerce.service.impl;

import com.zekaya.eccommerce.dto.CategoryDto;
import com.zekaya.eccommerce.dto.Response;
import com.zekaya.eccommerce.entity.Category;
import com.zekaya.eccommerce.exception.NotFoundException;
import com.zekaya.eccommerce.mapper.EntityDtoMapper;
import com.zekaya.eccommerce.repository.CategoryRepo;
import com.zekaya.eccommerce.service.interf.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl  implements CategoryService  {

    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response createCategory(CategoryDto categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepo.save(category);
        return Response.builder()
                .status(200)
                .message("Category created successfully")
                .build();
    }

    @Override
    public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        category.setName(categoryRequest.getName());
        categoryRepo.save(category);
        return  Response.builder()
                .status(200)
                .message("Category Updated Successfully")
                .build();
    }

    @Override
    public Response getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDtoList = categories.stream()
                .map(entityDtoMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .categoryList(categoryDtoList)
                .build();
    }


    @Override
    public Response getCategoryById(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        CategoryDto categoryDto =  entityDtoMapper.categoryToCategoryDto(category);
        return Response.builder()
                .status(200)
                .category(categoryDto)
                .build();
    }

    @Override
    public Response deleteCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        categoryRepo.delete(category);
        return Response.builder()
                .status(200)
                .message("Category Deleted Successfully")
                .build();
    }
}
