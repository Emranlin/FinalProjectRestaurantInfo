package peaksoft.service.impl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.subCategory.SubCategoriesResponse;
import peaksoft.dto.response.subCategory.SubCategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.SubCategoryService;
import java.util.*;
import java.util.stream.Collectors;



@Service
public class SubCategoriesImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    public SubCategoriesImpl(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;

        this.categoryRepository = categoryRepository;
    }

    @Override
    public SimpleResponse saveSubCategories(SubCategoryRequest subCategoryRequest) {
        Category category = categoryRepository.findById(subCategoryRequest.categoryId())
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Category with id: %s not found", subCategoryRequest.categoryId())));

        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryRequest.name());
        subCategory.setCategory(category);
        subCategoryRepository.save(subCategory);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("SubCategory with name: %s saved successfully", subCategory.getName())).build();
    }

    @Override
    public List<SubCategoriesResponse> getAllSubCat(Long id) {
        return subCategoryRepository.getAllSubcategories(id);
    }

    @Override
    public SubCategoryResponse getById(Long subCatId) {
        Category category = categoryRepository.findById(subCatId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Category with id: %s not found", subCatId)));

        return subCategoryRepository.getSubCategoriesById(subCatId);

    }

    @Override
    public SimpleResponse deleteSubCat(Long subCatId) {
        Category category = categoryRepository.findById(subCatId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Category with id: %s not found", subCatId)));
        subCategoryRepository.deleteById(subCatId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("SubCategory with id: %s saved successfully", subCatId)).build();
    }

    @Override
    public SimpleResponse updateSubCat(Long subCatId, SubCategoryRequest subCategoryRequest) {
        SubCategory subCategory = subCategoryRepository.findById(subCatId).orElseThrow(() -> new NoSuchElementException(String.format("SubCategory with id: %s not found", subCatId)));
        subCategory.setName(subCategoryRequest.name());
        subCategoryRepository.save(subCategory);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("SubCategory with name: %s updated successfully", subCategory.getName())).build();
    }

    @Override
    public Map<String, List<SubCategoriesResponse>> findAllWithSubcategories() {
        return  subCategoryRepository.getAllSubcategoriesWithCategory().stream()
                .collect(Collectors.groupingBy(SubCategoriesResponse::getCategoryName));
    }

    @Override
    public List<SubCategoriesResponse> sortByName(String word) {
        List<SubCategoriesResponse>subCategories;
        if (word.equals("asc")){
            subCategories=subCategoryRepository.sortByNameAsc(word);
        }else {
            subCategories=subCategoryRepository.sortByNameDesc(word);
        }
        return subCategories;
    }
}
