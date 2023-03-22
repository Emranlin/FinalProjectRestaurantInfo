package peaksoft.service;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.category.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;


import java.util.List;

public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> getAllCategory();
    CategoryResponse getCategoryById(Long id);
    SimpleResponse deleteCategory(Long categoryId);
    SimpleResponse updateCategory(Long categoryId,CategoryRequest categoryRequest);

}
