package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.category.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryApi {

    private final CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.saveCategory(categoryRequest);
    }

    @PermitAll
    @GetMapping
    public List<CategoryResponse> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{categoryId}")
    public CategoryResponse getById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")

    @PutMapping("/{categoryId}")
    public SimpleResponse updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(categoryId, categoryRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public SimpleResponse deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
