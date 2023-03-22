package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.subCategory.SubCategoriesResponse;
import peaksoft.dto.response.subCategory.SubCategoryResponse;
import peaksoft.entity.SubCategory;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query("select new peaksoft.dto.response.subCategory.SubCategoriesResponse(s.name,s.category.name)from SubCategory s")
    List<SubCategoriesResponse>getAllSubcategories(Long id);
    SubCategoryResponse getSubCategoriesById(Long subCatId);
    @Query("select new peaksoft.dto.response.subCategory.SubCategoriesResponse(s.category.name, s.name)from SubCategory s")
    List<SubCategoriesResponse>getAllSubcategoriesWithCategory();
    @Query("SELECT new peaksoft.dto.response.subCategory.SubCategoriesResponse(s.name,s.category.name) FROM SubCategory s ORDER BY s.name asc")
    List<SubCategoriesResponse>sortByNameAsc(String name);
    @Query("SELECT new peaksoft.dto.response.subCategory.SubCategoriesResponse(s.name,s.category.name) FROM SubCategory s ORDER BY s.name desc")
    List<SubCategoriesResponse>sortByNameDesc(String name);
    Optional<SubCategory>findByName(String name);

}