package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.stopList.StopListResponse;
import peaksoft.entity.StopList;

import java.time.LocalDate;
import java.util.List;

public interface StopListRepository extends JpaRepository<StopList, Long> {
    @Query("select new peaksoft.dto.response.stopList.StopListResponse(s.menuItem.name,s.reason,s.date) FROM StopList s  WHERE s.date <> CURRENT_DATE ")
    List<StopListResponse> getAllStopList();

    @Query("select new peaksoft.dto.response.stopList.StopListResponse(s.menuItem.name,s.reason,s.date) from StopList s where s.id=:stopListId")
    StopListResponse getStopListById(Long stopListId);
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM MenuItem m "
            + "JOIN m.stopList sl "
            + "WHERE sl.date = :date AND m.name = :name")
    boolean existsByDateAndMenuItemName(@Param("date") LocalDate date, @Param("name") String name);
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM MenuItem m "
            + "JOIN m.stopList sl "
            + "WHERE sl.date = :date AND m.name = :name AND sl.id = :stopListId")
    boolean existsByDateAndMenuItemNameAndDate(@Param("date") LocalDate date, @Param("name") String name, @Param("stopListId") Long stopListId);

}