package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.cheque.ChequeResponse;
import peaksoft.dto.response.cheque.ChequeResponseSumPerDay;
import peaksoft.dto.response.cheque.ChequeRestaurantAverage;
import peaksoft.entity.Cheque;

import java.time.LocalDate;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {

//   @Query("select avg(m.price) from Restaurant r join r.users u join u.cheques c join c.menuItems m where r.id=1 and c.createdAt=:date")
//  Integer avg(LocalDate date);
//}
}