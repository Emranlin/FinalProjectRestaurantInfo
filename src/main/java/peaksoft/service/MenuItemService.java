package peaksoft.service;

import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.*;
import peaksoft.dto.response.menuItem.MenuItemResponse;
import peaksoft.dto.response.menuItem.MenuItemResponseFilter;
import peaksoft.dto.response.menuItem.MenuItemResponseSearch;
import peaksoft.dto.response.menuItem.MenuItemResponseSort;
import peaksoft.dto.response.pagination.PaginationResponse;
import peaksoft.dto.response.pagination.PaginationResponseMenuItem;

import java.util.List;

public interface MenuItemService {
SimpleResponse saveMenuItem(MenuItemRequest menuItemRequest);
List<MenuItemResponse> getAllMenuItem();
MenuItemResponse getMenuItemById(Long menuItemId);
SimpleResponse deleteMenuItem(Long menuItemId);
SimpleResponse updateMenuItem(Long menuItemId,MenuItemRequest menuItemRequest);
List<MenuItemResponseSearch>globalSearch(String keyWord);
List<MenuItemResponseSort>sortByPrice(String word);
List<MenuItemResponseFilter>filterByVegetarian(Boolean isTrue);
PaginationResponseMenuItem getMenuItemPagination(int page, int size);
}
