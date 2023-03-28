package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.menuItem.*;
import peaksoft.dto.response.user.UserResponseGetAll;
import peaksoft.entity.MenuItem;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("select new peaksoft.dto.response.menuItem.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)from  MenuItem m where m.stopList.date<>current date")
    List<MenuItemResponse> getAllMenuItem();
    List<MenuItemResponse> getAllByStopListNull();
    MenuItemResponse getMenuItemById(Long menuItemId);
    @Query("SELECT new peaksoft.dto.response.menuItem.MenuItemResponseSearch(c.name,s.name,mi.name,mi.image,mi.price) FROM MenuItem  mi join  mi.subCategories s join s.category c where (mi.name ILIKE %:keyWord% OR c.name ILIKE %:keyWord% OR s.name ILIKE %:keyWord%)")
    List<MenuItemResponseSearch>globalSearch(String keyWord);
    @Query("SELECT new peaksoft.dto.response.menuItem.MenuItemResponseSort(m.name,m.price) FROM MenuItem m ORDER BY m.price asc")
    List<MenuItemResponseSort>sortByPriceAsc(String word);

    @Query("SELECT new peaksoft.dto.response.menuItem.MenuItemResponseSort(m.name,m.price) FROM MenuItem m ORDER BY m.price desc ")
    List<MenuItemResponseSort>sortByPriceDesc( String word);
    @Query("SELECT new peaksoft.dto.response.menuItem.MenuItemResponseFilter(m.name, m.isVegetarian) FROM MenuItem m WHERE m.isVegetarian = :isTrue")
    List<MenuItemResponseFilter>filterByVegetarian(Boolean isTrue);
    Page<MenuItemResponse> findAllProjectedBy(Pageable pageable);

}