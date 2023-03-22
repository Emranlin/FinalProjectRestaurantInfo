package peaksoft.service.impl;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.category.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.repository.CategoryRepository;
import peaksoft.service.CategoryService;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {
    private  final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.findAll().stream().anyMatch(s->s.getName().equals(categoryRequest.name()))) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message(String.format("Category with name: %s already exists!", categoryRequest.name()))
                    .build();
        }
        Category category=new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Category with name %s successful saved",categoryRequest.name())).build();
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.getAllCategory();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException(
                        String.format("Category with ID: %s not found",id)));
        return categoryRepository.getCategoryById(id);
    }

    @Override
    public SimpleResponse deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message(String.format("Category with id: %s not found!", categoryId))
                    .build();
        }
        categoryRepository.deleteById(categoryId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Category with %s id is deleted",categoryId)).build();
    }

    @Override
    public SimpleResponse updateCategory(Long categoryId,CategoryRequest categoryRequest) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Category with id: %s not found!", categoryId)));

        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("Category with %s id is successful updated",categoryId)).build();
    }
}
