package peaksoft.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.*;
import peaksoft.dto.response.menuItem.MenuItemResponse;
import peaksoft.dto.response.menuItem.MenuItemResponseFilter;
import peaksoft.dto.response.menuItem.MenuItemResponseSearch;
import peaksoft.dto.response.menuItem.MenuItemResponseSort;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.SubCategory;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private  final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository, SubCategoryRepository subCategoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public SimpleResponse saveMenuItem(MenuItemRequest menuItemRequest) {
        Restaurant restaurant = restaurantRepository.findById(menuItemRequest.restaurantId()).orElseThrow(() -> new NotFoundException(String.format("Restaurant with id:%s not found", menuItemRequest.restaurantId())));
        SubCategory subCategory = subCategoryRepository.findByName(menuItemRequest.subCategoryName()).orElseThrow(() -> new NotFoundException(String.format("SubCategory with name: %s not found", menuItemRequest.subCategoryName())));
        MenuItem menuItem=new MenuItem();
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setIsVegetarian(menuItemRequest.isVegetarian());
        menuItem.setRestaurant(restaurant);
        menuItem.addSubCategory(subCategory);
        subCategory.setMenuItem(menuItem);
        menuItemRepository.save(menuItem);
        subCategoryRepository.save(subCategory);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("MenuItem with name: %s successfully saved",menuItem.getName())).build();
    }

    @Override
    public List<MenuItemResponse> getAllMenuItem() {
        List<MenuItemResponse> list = new ArrayList<>();
        list.addAll(menuItemRepository.getAllMenuItem());
        list.addAll(menuItemRepository.getAllByStopListNull());
        return list;
    }

    @Override
    public MenuItemResponse getMenuItemById(Long menuItemId) {
        return menuItemRepository.getMenuItemById(menuItemId);
    }

    @Override
    public SimpleResponse deleteMenuItem(Long menuItemId) {
        menuItemRepository.deleteById(menuItemId);
        return  SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("MenuItem with id: %s successfully deleted",menuItemId)).build();
    }

    @Override
    public SimpleResponse updateMenuItem(Long menuItemId, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new NoSuchElementException("Not found "));
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setIsVegetarian(menuItemRequest.isVegetarian());
        menuItemRepository.save(menuItem);
        return  SimpleResponse.builder().httpStatus(HttpStatus.OK).message(String.format("MenuItem with id: %s successfully saved",menuItemId)).build();
    }

    @Override
    public List<MenuItemResponseSearch> globalSearch(String keyWord) {
        return menuItemRepository.globalSearch(keyWord);
    }

    @Override
    public List<MenuItemResponseSort> sortByPrice(String word) {
        List<MenuItemResponseSort> menuItems;
      if(word.equals("asc")){
          menuItems=menuItemRepository.sortByPriceAsc(word);
      }else {
         menuItems= menuItemRepository.sortByPriceDesc(word);
      }
        return menuItems;
    }

    @Override
    public List<MenuItemResponseFilter> filterByVegetarian(Boolean isTrue) {
        return menuItemRepository.filterByVegetarian(isTrue);
    }
}
