package peaksoft.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.category.CategoryResponse;
import peaksoft.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select new peaksoft.dto.response.category.CategoryResponse(c.name)from Category  c  ")
    List<CategoryResponse> getAllCategory();
    CategoryResponse getCategoryById(Long id);
}