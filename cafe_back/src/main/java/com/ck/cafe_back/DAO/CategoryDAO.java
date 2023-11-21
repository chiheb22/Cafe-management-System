package com.ck.cafe_back.DAO;

import com.ck.cafe_back.POJO.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
    List<Category> getAllCategories();
}
