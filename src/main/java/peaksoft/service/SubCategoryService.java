package peaksoft.service;

import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.subCategory.SubCategoriesResponse;
import peaksoft.dto.response.subCategory.SubCategoryResponse;

import java.util.List;
import java.util.Map;

public interface SubCategoryService {
    SimpleResponse saveSubCategories(SubCategoryRequest subCategoryRequest);
    List<SubCategoriesResponse> getAllSubCat(Long id);
    SubCategoryResponse getById(Long subCatId);
    SimpleResponse deleteSubCat(Long subCatId);
    SimpleResponse updateSubCat(Long subCatId,SubCategoryRequest subCategoryRequest);
    Map<String,List<SubCategoriesResponse>>findAllWithSubcategories();
    List<SubCategoriesResponse>sortByName(String word);


}
