package peaksoft.api;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.*;
import peaksoft.dto.response.menuItem.MenuItemResponse;
import peaksoft.dto.response.menuItem.MenuItemResponseFilter;
import peaksoft.dto.response.menuItem.MenuItemResponseSearch;
import peaksoft.dto.response.menuItem.MenuItemResponseSort;
import peaksoft.dto.response.pagination.PaginationResponse;
import peaksoft.dto.response.pagination.PaginationResponseMenuItem;
import peaksoft.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/menuItems")
public class MenuItemApi {
    private final MenuItemService service;

    public MenuItemApi(MenuItemService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
    public SimpleResponse saveMenuItem(@RequestBody MenuItemRequest menuItemRequest) {
        return service.saveMenuItem(menuItemRequest);
    }

   @PermitAll
    @GetMapping
    public List<MenuItemResponse> getAllMenuItem() {
        return service.getAllMenuItem();
    }
    @PermitAll
    @GetMapping("/{menuItemId}")
    public MenuItemResponse getByIdMenu(@PathVariable Long menuItemId) {
        return service.getMenuItemById(menuItemId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{menuItemId}")
    public SimpleResponse updateMenu(@PathVariable Long menuItemId, @RequestBody MenuItemRequest menuItemRequest) {
        return service.updateMenuItem(menuItemId, menuItemRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{menuItemId}")
    public SimpleResponse deleteMenu(@PathVariable Long menuItemId) {
        return service.deleteMenuItem(menuItemId);
    }
    @PermitAll
    @GetMapping("/search")
    public List<MenuItemResponseSearch> globalSearch(@RequestParam(required = false) String keyWord) {
        return service.globalSearch(keyWord);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @GetMapping("/sort")
    public List<MenuItemResponseSort> sortByPrice(@RequestParam(required = false) String word) {
        return service.sortByPrice(word);
    }
   @PermitAll
    @GetMapping("/filter")
    public List<MenuItemResponseFilter> filterByName(@RequestParam(required = false) Boolean isTrue) {
        return service.filterByVegetarian(isTrue);
    }
    @GetMapping("/pagination")
    public PaginationResponseMenuItem getMenuItemPagination(@RequestParam int page, @RequestParam int size){
        return service.getMenuItemPagination(page,size);
    }
}
