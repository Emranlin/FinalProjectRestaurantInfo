package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.subCategory.SubCategoriesResponse;
import peaksoft.dto.response.subCategory.SubCategoryResponse;
import peaksoft.service.SubCategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subCategory")
public class SubCategory {
    private final SubCategoryService service;

    public SubCategory(SubCategoryService service) {
        this.service = service;
    }

    @PermitAll
    @GetMapping
    public List<SubCategoriesResponse> getAllSubCategory(@RequestParam(value = "id", required = false) Long id) {
        return service.getAllSubCat(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveSubCategory(@RequestBody SubCategoryRequest subCategoryRequest) {
        return service.saveSubCategories(subCategoryRequest);
    }

    @PermitAll
    @GetMapping("/{subCategoryId}")
    public SubCategoryResponse getById(@PathVariable Long subCategoryId) {
        return service.getById(subCategoryId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{subCategoryId}")
    public SimpleResponse updateSubCategory(@PathVariable Long subCategoryId, @RequestBody SubCategoryRequest subCategoryRequest) {
        return service.updateSubCat(subCategoryId, subCategoryRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{subCategoryId}")
    public SimpleResponse deleteSubCategory(@PathVariable Long subCategoryId) {
        return service.deleteSubCat(subCategoryId);
    }

    @PermitAll
    @GetMapping("/group")
    public Map<String, List<SubCategoriesResponse>> findWithCategories() {
        return service.findAllWithSubcategories();
    }

    @PermitAll
    @GetMapping("/sort")
    public List<SubCategoriesResponse> sortByName(@RequestParam(required = false) String word) {
        return service.sortByName(word);
    }
}
