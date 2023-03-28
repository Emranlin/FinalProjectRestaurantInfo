package peaksoft.dto.response.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.dto.response.menuItem.MenuItemResponse;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponseMenuItem {
   private List<MenuItemResponse> menuItemResponseList;
   private int currentPage;
   private int pageSize;
}
