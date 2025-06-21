package com.example.yemek_tarifi.service.impl;

import com.example.yemek_tarifi.dto.CategoryRequestDTO;
import com.example.yemek_tarifi.dto.CategoryResponseDTO;
import com.example.yemek_tarifi.entity.Category;
import com.example.yemek_tarifi.repository.CategoryRepository;
import com.example.yemek_tarifi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> modelMapper.map( category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryResponseDTO> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class));
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {
        // RequestDTO'yu entity'ye dönüştür
        Category category = modelMapper.map(requestDTO, Category.class);
        // Veritabanına kaydet
        Category savedCategory = categoryRepository.save(category);
        // Kaydedilen entity'i ResponseDTO'ya dönüştür ve döndür
        return modelMapper.map(savedCategory, CategoryResponseDTO.class);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {
        // Önce kategoriyi bul, yoksa hata fırlat
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı: " + id));

        // RequestDTO'daki değerleri mevcut kategoriye aktar
        modelMapper.map(requestDTO, existingCategory);

        // Güncellenmiş kategoriyi kaydet
        Category updatedCategory = categoryRepository.save(existingCategory);

        // ResponseDTO'ya dönüştür ve döndür
        return modelMapper.map(updatedCategory, CategoryResponseDTO.class);
    }

    @Override
    public void deleteCategory(Long id) {
        // Kategori var mı kontrol et
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Kategori bulunamadı: " + id);
        }
        // Kategoriyi sil
        categoryRepository.deleteById(id);
    }
}
