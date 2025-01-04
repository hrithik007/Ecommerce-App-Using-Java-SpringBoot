package com.ecommerce.webapp.service;
import com.ecommerce.webapp.model.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CategoryService {

    public Category saveCategory(Category category);

    public List<Category> getAllCategory();
    public List<Category> getAllActiveCategory();
    public Boolean existCategory(String name);
    public Boolean deleteCategory(int id);
    public Category getCategoryById(int id);
    public Page<Category> getAllCategorPagination(Integer pageNo, Integer pageSize);
}
