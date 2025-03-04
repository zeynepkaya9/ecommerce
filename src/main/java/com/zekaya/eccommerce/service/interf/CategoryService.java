package com.zekaya.eccommerce.service.interf;

import com.zekaya.eccommerce.dto.CategoryDto;
import com.zekaya.eccommerce.dto.Response;

public interface CategoryService {

    Response createCategory(CategoryDto categoryRequest);

    Response updateCategory(Long categoryId, CategoryDto categoryRequest);

    Response getAllCategories();

    Response getCategoryById(Long categoryId);

    Response deleteCategory(Long categoryId);
}
