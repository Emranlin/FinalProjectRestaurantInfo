package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.response.cheque.ChequeResponse;
import peaksoft.dto.response.cheque.ChequeResponseSumPerDay;
import peaksoft.dto.response.cheque.ChequeRestaurantAverage;
import peaksoft.dto.response.menuItem.MenuItemForCheque;
import peaksoft.entity.Cheque;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {

   @Query("select avg(m.price) from Restaurant r join r.users u join u.cheques c join c.menuItems m where r.id=1 and c.createdAt=:date")
  Integer avg(LocalDate date);
  @Query("SELECT NEW peaksoft.dto.response.menuItem.MenuItemForCheque(m.id, m.name, m.price, COUNT(m)) from MenuItem  m join m.cheques c where c.id=:chequeId GROUP BY m.id, m.name, m.price")
  List<MenuItemForCheque> getMealsByChequeId(Long chequeId);

    @Query("SELECT c FROM Cheque c WHERE c.user.id = :userId AND c.createdAt = :date")
    List<Cheque> findByUser_IdAndCreatedAt(@Param("userId") Long userId, @Param("date") LocalDate date);
    @Query("select new peaksoft.dto.response.menuItem" +
            ".MenuItemForCheque(m.id, m.name, m.price, " +
            "count(m)) from MenuItem m join m.cheques c where c.id = ?1 group by m.id, m.name, m.price")
    List<MenuItemForCheque> getMenuItem(Long chequeId);


}