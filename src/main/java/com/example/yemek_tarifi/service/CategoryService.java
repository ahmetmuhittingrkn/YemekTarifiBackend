package com.example.yemek_tarifi.service;

import com.example.yemek_tarifi.dto.CategoryRequestDTO;
import com.example.yemek_tarifi.dto.CategoryResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryResponseDTO> getAllCategories();

    Optional<CategoryResponseDTO> getCategoryById(Long id);

    CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);

    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO);

    void deleteCategory(Long id);

}
