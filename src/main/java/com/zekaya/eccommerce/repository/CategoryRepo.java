package com.zekaya.eccommerce.repository;

import com.zekaya.eccommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
